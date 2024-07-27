document.addEventListener('DOMContentLoaded', function () {
    console.log("DOM fully loaded and parsed");

    // Element references
    const elements = {
        ingredientList: document.getElementById("ingredientList"),
        recipeStepList: document.getElementById("recipeStepList"),
        tagList: document.getElementById("tagList"),
        descriptionTextarea: document.getElementById('description'),
        charCount: document.getElementById('charCount'),
        recipeForm: document.getElementById('recipeForm'),
        nameInput: document.getElementById('name'),
        recipeImageInput: document.getElementById('recipeImage'),
        levelInput: document.getElementById('level'),
        timeInput: document.getElementById('time'),
        userIdInput: document.getElementById('userId'),
        directorySelect: document.getElementById('directory'),
        recipeIdInput: document.getElementById('recipeId'),
        addIngredientBtn: document.getElementById("addIngredientBtn"),
        addTagBtn: document.getElementById("addTagBtn"),
        addRecipeStepBtn: document.getElementById('addRecipeStepBtn'),
        ingredientsInput: document.getElementById("ingredientsInput"),
        tagsInput: document.getElementById("tagsInput"),
        saveButton: document.getElementById('saveButton'),
        addDirBtn: document.getElementById('addDirBtn'),
        saveDirBtn: document.getElementById('saveDir'),
        createDirModal: new bootstrap.Modal(document.getElementById('createDirModal'))
    };

    // Utility functions
    function collectItems(container) {
        return Array.from(container.children).map(item => item.textContent.slice(0, -1).trim());
    }

    function updateCharCount() {
        elements.charCount.textContent = `${elements.descriptionTextarea.value.length} 글자`;
    }

    function collectRecipeSteps(container) {
        return Array.from(container.children).map(step => ({
            stepNumber: parseInt(step.querySelector('.step-number').value, 10),
            description: step.querySelector('.step-description').value,
            stepImage: step.querySelector('.step-image').files[0] || null
        }));
    }

    function createBadge(text, onRemove) {
        const badge = document.createElement('div');
        badge.classList.add('badge', 'bg-secondary', 'me-2');
        badge.textContent = text;

        const removeBadgeBtn = document.createElement('span');
        removeBadgeBtn.classList.add('badge', 'bg-danger', 'ms-1');
        removeBadgeBtn.style.cursor = 'pointer';
        removeBadgeBtn.textContent = 'x';
        removeBadgeBtn.addEventListener('click', onRemove);

        badge.appendChild(removeBadgeBtn);
        return badge;
    }

    function addItemToList(inputId, listContainer, onRemove) {
        const input = document.getElementById(inputId);
        const value = input.value.trim();
        if (value) {
            const existingItems = Array.from(listContainer.children).map(item => item.textContent.slice(0, -1).trim());
            if (!existingItems.includes(value)) {
                const badge = createBadge(value, () => badge.remove());
                listContainer.appendChild(badge);
                input.value = "";
            } else {
                alert("이 항목은 이미 목록에 있습니다.");
            }
        } else {
            alert("유효한 값을 입력하세요.");
        }
    }

    function addRecipeStep() {
        const stepContainer = document.createElement('div');
        stepContainer.classList.add('step-container');
        const stepNumber = elements.recipeStepList.children.length + 1;

        const stepHeader = document.createElement('div');
        stepHeader.classList.add('step-header');
        stepHeader.innerHTML = `
            <h5 class="step-number-title">단계 ${stepNumber}</h5>
            <span class="remove-step-btn" style="cursor: pointer;">x</span>
        `;
        stepHeader.querySelector('.remove-step-btn').addEventListener('click', () => stepContainer.remove());

        const stepNumberInput = document.createElement('input');
        stepNumberInput.type = 'hidden';
        stepNumberInput.classList.add('step-number');
        stepNumberInput.value = stepNumber;

        const stepDescriptionInput = document.createElement('textarea');
        stepDescriptionInput.classList.add('form-control', 'step-description');
        stepDescriptionInput.placeholder = '조리 단계를 입력하세요.';

        const stepImageInput = document.createElement('input');
        stepImageInput.type = 'file';
        stepImageInput.classList.add('form-control', 'step-image');

        const stepImagePreview = document.createElement('img');
        stepImagePreview.classList.add('step-image-preview');
        stepImagePreview.style.display = 'none';

        const deleteStepImageBtn = document.createElement('button');
        deleteStepImageBtn.type = 'button';
        deleteStepImageBtn.classList.add('btn', 'btn-danger', 'mt-2', 'delete-step-image-btn');
        deleteStepImageBtn.textContent = '이미지 삭제';

        stepContainer.append(stepHeader, stepNumberInput, stepDescriptionInput, stepImageInput, stepImagePreview, deleteStepImageBtn);
        elements.recipeStepList.appendChild(stepContainer);
    }

    // Event listeners
    elements.descriptionTextarea.addEventListener('input', updateCharCount);
    elements.recipeForm.addEventListener('submit', async function (event) {
        event.preventDefault();
        console.log("Form submit event triggered");

        const formData = new FormData();
        const steps = collectRecipeSteps(elements.recipeStepList);

        formData.append('name', elements.nameInput.value);
        formData.append('description', elements.descriptionTextarea.value);
        collectItems(elements.ingredientList).forEach(ingredient => formData.append('ingredients', ingredient));
        collectItems(elements.tagList).forEach(tag => formData.append('tags', tag));
        formData.append('level', elements.levelInput.value);
        formData.append('time', elements.timeInput.value);
        formData.append('userId', elements.userIdInput.value);
        formData.append('dirId', elements.directorySelect.value);

        if (elements.recipeImageInput.files[0]) {
            formData.append('recipeImage', elements.recipeImageInput.files[0]);
        }

        steps.forEach((step, index) => {
            formData.append(`steps[${index}].stepNumber`, step.stepNumber);
            formData.append(`steps[${index}].description`, step.description);
            if (step.stepImage) {
                formData.append(`steps[${index}].stepImage`, step.stepImage);
            }
        });

        const recipeId = elements.recipeIdInput.value;

        try {
            const response = await fetch(`/api/recipes/${recipeId}/edit`, {
                method: 'PUT',
                body: formData
            });

            if (response.ok) {
                window.location.href = '/recipe/list';
            } else {
                alert('Failed to update the recipe');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    });

    elements.addIngredientBtn.addEventListener("click", () => addItemToList("ingredientsInput", elements.ingredientList));
    elements.addTagBtn.addEventListener("click", () => addItemToList("tagsInput", elements.tagList));
    elements.addRecipeStepBtn.addEventListener('click', addRecipeStep);

    elements.ingredientsInput.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            addItemToList("ingredientsInput", elements.ingredientList);
        }
    });

    elements.tagsInput.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            event.preventDefault();
            addItemToList("tagsInput", elements.tagList);
        }
    });

    let deletedImages = [];

    elements.recipeStepList.addEventListener('click', function (event) {
        if (event.target.classList.contains('delete-step-image-btn')) {
            const stepContainer = event.target.closest('.step-container');
            const imagePreview = stepContainer.querySelector('.step-image-preview');

            // Clear image source and hide preview
            imagePreview.src = '';
            imagePreview.style.display = 'none';

            // Clear file input (if necessary)
            const fileInput = stepContainer.querySelector('.step-image');
            fileInput.value = ''; // Clear file input

            // Optionally handle any server-side image deletion here
            const imageId = event.target.getAttribute('data-image-id');
            console.log(`Image with ID ${imageId} deleted.`);

            // Add imageId to deletedImages array
            if (imageId) {
                deletedImages.push(imageId);
            }
        }
    });

    // Handle file input change event for preview
    elements.recipeStepList.addEventListener('change', function (event) {
        if (event.target.classList.contains('step-image')) {
            const file = event.target.files[0];
            const preview = event.target.nextElementSibling;

            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                };
                reader.readAsDataURL(file);
            } else {
                preview.style.display = 'none';
            }
        }
    });

    elements.saveButton.addEventListener('click', function () {
        fetch('/api/files/delete', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({imageIds: deletedImages})
        }).then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete images');
            }
            deletedImages = [];
        }).catch(error => {
            console.error('Image deletion failed:', error);
        });
    });

    elements.addDirBtn.addEventListener('click', function () {
        elements.createDirModal.show();
    });

    elements.saveDirBtn.addEventListener('click', async function () {
        const newDirName = document.getElementById('newDirName').value.trim();
        if (newDirName) {
            try {
                const response = await fetch('/api/directories', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({name: newDirName})
                });

                if (response.ok) {
                    const newDir = await response.json();
                    const newOption = document.createElement('option');
                    newOption.value = newDir.id;
                    newOption.textContent = newDir.name;
                    newOption.selected = true;
                    elements.directorySelect.appendChild(newOption);
                    elements.createDirModal.hide();
                } else {
                    alert('Failed to create new directory');
                }
            } catch (error) {
                console.error('Error:', error);
            }
        }
    });
});
