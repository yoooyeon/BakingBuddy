document.addEventListener('DOMContentLoaded', function () {
    console.log("DOM fully loaded and parsed");

    const ingredientList = document.getElementById("ingredientList");
    const recipeStepList = document.getElementById("recipeStepList");
    const tagList = document.getElementById("tagList");

    function collectItems(container) {
        return Array.from(container.children).map(item => item.textContent.slice(0, -1).trim());
    }

    function collectRecipeSteps(container) {
        return Array.from(container.children).map(step => ({
            stepNumber: parseInt(step.querySelector('.step-number').value, 10),
            description: step.querySelector('.step-description').value,
            stepImage: step.querySelector('.step-image').files[0] ? step.querySelector('.step-image').files[0] : null
        }));
    }

    document.getElementById('recipeForm').addEventListener('submit', async function (event) {
        event.preventDefault();
        console.log("Form submit event triggered");

        const formData = new FormData();

        // Collect form data
        const name = document.getElementById('name').value;
        const description = document.getElementById('description').value;
        const ingredients = collectItems(ingredientList);
        const tags = collectItems(tagList);
        const recipeImage = document.getElementById('recipeImage').files[0];
        const level = document.getElementById('level').value;
        const time = document.getElementById('time').value;
        const userId = document.getElementById('userId').value;
        const dirId = document.getElementById('directory').value;
        console.log("Collected data", {name, description, ingredients, tags, recipeImage});

        // Create the DTO object
        const dto = {
            name: name,
            description: description,
            ingredients: ingredients,
            tags: tags,
            level: level,
            time: time,
            userId: userId,
            dirId: dirId,
        };

        // Append DTO and recipe image to FormData
        formData.append('dto', new Blob([JSON.stringify(dto)], {type: 'application/json'}));
        if (recipeImage) {
            formData.append('recipeImage', recipeImage);
        }

        try {
            // 1. Save recipe
            const response = await fetch('/api/recipes', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            // Ensure the response is in JSON format
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.indexOf('application/json') !== -1) {
                const savedRecipe = await response.json();
                console.log("Saved Recipe:", savedRecipe);
                const recipeId = savedRecipe.id;
                console.log("recipeId=", recipeId)

                // 2. Add steps
                const steps = collectRecipeSteps(recipeStepList);
                console.log("Collected steps", steps);

                const stepPromises = steps.map(async (step) => {
                    const stepData = new FormData();
                    stepData.append('stepNumber', step.stepNumber);
                    stepData.append('description', step.description);
                    if (step.stepImage) {
                        stepData.append('stepImage', step.stepImage);
                    }
                    stepData.append('recipeId', recipeId);

                    try {
                        const stepResponse = await fetch(`/api/recipes/${recipeId}/steps`, {
                            method: 'POST',
                            body: stepData
                        });

                        if (!stepResponse.ok) {
                            throw new Error(`HTTP error! status: ${stepResponse.status}`);
                        }

                        const stepResult = await stepResponse.json();
                        console.log('Step added:', stepResult);

                        // Update the UI with the new step
                        addStepToList(stepResult); // Implement this function to update the UI

                    } catch (error) {
                        console.error('Error adding step:', error);
                    }
                });

                // Wait for all step addition promises to resolve
                await Promise.all(stepPromises);

                // Redirect or update UI as needed after all steps are added
                window.location.href = `/api/recipes/${recipeId}`;

            } else {
                console.error('Unexpected content type:', contentType);
            }
        } catch (error) {
            console.error('Error saving recipe:', error);
        }
    });



    function addStepToList() {
        const stepContainer = document.createElement('div');
        stepContainer.className = 'step-container';

        const stepHeader = document.createElement('div');
        stepHeader.className = 'step-header';

        const stepNumber = document.createElement('h5');
        stepNumber.className = 'step-number-title';
        stepNumber.textContent = `단계 ${recipeStepList.children.length + 1}`;

        const removeButton = document.createElement('span');
        removeButton.className = 'remove-step-btn';
        removeButton.textContent = 'x';
        removeButton.addEventListener('click', () => {
            stepContainer.remove();
            updateStepNumbers();
        });

        stepHeader.appendChild(stepNumber);
        stepHeader.appendChild(removeButton);

        const stepNumberInput = document.createElement('input');
        stepNumberInput.type = 'hidden';
        stepNumberInput.className = 'step-number';
        stepNumberInput.value = recipeStepList.children.length + 1;

        const stepDescription = document.createElement('textarea');
        stepDescription.className = 'form-control step-description';
        stepDescription.placeholder = '단계 설명을 입력하세요';

        const stepImageInput = document.createElement('input');
        stepImageInput.type = 'file';
        stepImageInput.className = 'form-control step-image';

        stepContainer.appendChild(stepHeader);
        stepContainer.appendChild(stepNumberInput);
        stepContainer.appendChild(stepDescription);
        stepContainer.appendChild(stepImageInput);
        recipeStepList.appendChild(stepContainer);
    }

    function updateStepNumbers() {
        Array.from(recipeStepList.children).forEach((step, index) => {
            step.querySelector('.step-number-title').textContent = `단계 ${index + 1}`;
            step.querySelector('.step-number').value = index + 1;
        });
    }

    document.getElementById('addRecipeStepBtn').addEventListener('click', addStepToList);

    function addItemToList(inputId, listContainer) {
        const input = document.getElementById(inputId);
        const inputValue = input.value.trim();
        if (inputValue) {
            const existingItems = Array.from(listContainer.children).map(item => item.textContent.slice(0, -1).trim());
            if (!existingItems.includes(inputValue)) {
                const itemContainer = document.createElement("h3");
                itemContainer.className = "badge bg-dark me-2";
                itemContainer.textContent = inputValue;

                const closeButton = document.createElement("span");
                closeButton.className = "badge bg-danger ms-1";
                closeButton.textContent = "x";
                closeButton.style.cursor = "pointer";
                closeButton.addEventListener("click", () => itemContainer.remove());

                itemContainer.appendChild(closeButton);
                listContainer.appendChild(itemContainer);
                input.value = ""; // Clear input after adding
            } else {
                alert("이 항목은 이미 목록에 있습니다.");
            }
        } else {
            alert("유효한 값을 입력하세요.");
        }
    }

    document.getElementById("addIngredientBtn").addEventListener("click", () => addItemToList("ingredientsInput", ingredientList));
    document.getElementById("addTagBtn").addEventListener("click", () => addItemToList("tagsInput", tagList));

    // Enter key event listeners for inputs
    document.getElementById("ingredientsInput").addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            addItemToList("ingredientsInput", ingredientList);
        }
    });

    document.getElementById("tagsInput").addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            addItemToList("tagsInput", tagList);
        }
    });

    // Modal handling
    const modal = document.getElementById("createDirModal");
    const btn = document.getElementById("addDirBtn");
    const closeBtn = document.getElementsByClassName("btn-close")[0];

    btn.addEventListener('click', () => modal.style.display = "block");
    closeBtn.addEventListener('click', () => modal.style.display = "none");
    window.addEventListener('click', (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    // Directory addition
    $('#saveDir').click(function () {
        const dirName = document.getElementById("newDirName").value;
        const userId = document.getElementById("userId").value;

        $.ajax({
            url: '/api/directories',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({name: dirName, userId: userId}),
            success: function (response) {
                $('#createDirModal').hide();
                $('#directory').append($('<option>', {
                    value: response.id,
                    text: response.name
                }));
            },
            error: function (xhr) {
                const errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : '디렉토리 저장 중 오류가 발생했습니다.';
                alert(errorMessage);
                console.error(xhr.responseText);
            }
        });
    });

    const descriptionInput = document.getElementById('description');
    const charCount = document.getElementById('charCount');

    descriptionInput.addEventListener('input', function () {
        charCount.textContent = `${descriptionInput.value.length} 글자`;
    });
});
