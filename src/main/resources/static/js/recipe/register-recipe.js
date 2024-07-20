document.addEventListener('DOMContentLoaded', function () {
    // Caching DOM elements
    const ingredientList = document.getElementById("ingredientList");
    const recipeStepList = document.getElementById("recipeStepList");
    const tagList = document.getElementById("tagList");

    // Collect items from a container
    function collectItems(container) {
        return Array.from(container.children).map(item => item.textContent.slice(0, -1).trim());
    }

    document.getElementById("recipeForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent form submission

        const formData = new FormData();
        const data = {
            userId: document.getElementById("userId").value,
            dirId: document.getElementById("directory").value,
            name: document.getElementById("name").value,
            memo: document.getElementById("memo").value,
            ingredients: collectItems(ingredientList),
            recipeSteps: Array.from(recipeStepList.children).map(step => ({
                stepNumber: parseInt(step.querySelector('.step-number').value, 10),
                description: step.querySelector('.step-description').value
            })),
            tags: collectItems(tagList),
            time: document.getElementById("time").value,
            level: document.getElementById("level").value
        };

        formData.append("dto", new Blob([JSON.stringify(data)], {type: "application/json"}));
        formData.append("recipeImage", document.getElementById("recipeImage").files[0]);

        // Add each step image to FormData
        Array.from(recipeStepList.children).forEach((step, index) => {
            const stepImage = step.querySelector('.step-image').files[0];
            if (stepImage) {
                formData.append('stepImages', stepImage);  // Use 'stepImages' as key for List<MultipartFile>
            }
        });

        $.ajax({
            url: '/api/recipes',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (response) {
                alert('레시피가 성공적으로 등록되었습니다!');
                window.location.href = `/api/recipes/${response.id}`;
            },
            error: function (error) {
                alert('레시피 등록 중 오류가 발생했습니다.');
                console.error(error);
            }
        });
    });


    // Add a new step to the list
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

    // Update step numbers
    function updateStepNumbers() {
        Array.from(recipeStepList.children).forEach((step, index) => {
            step.querySelector('.step-number-title').textContent = `단계 ${index + 1}`;
            step.querySelector('.step-number').value = index + 1;
        });
    }

    document.getElementById('addRecipeStepBtn').addEventListener('click', addStepToList);

    // Add item to list (ingredients or tags)
    function addItemToList(inputId, listContainer) {
        const input = document.getElementById(inputId);
        const inputValue = input.value.trim();
        if (inputValue) {
            const existingItems = Array.from(listContainer.children).map(item => item.textContent.slice(0, -1).trim());
            if (!existingItems.includes(inputValue)) {
                const itemContainer = document.createElement("div");
                itemContainer.className = "badge bg-secondary me-2";
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
                $('#createDirModal').modal('hide');
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
});
