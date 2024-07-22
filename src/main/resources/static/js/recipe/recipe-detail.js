let recipeId, userId;

document.addEventListener("DOMContentLoaded", function () {
    // 초기 값 세팅
    recipeId = document.getElementById('recipe-id').textContent;
    userId = document.getElementById('user-id').value;

    const likeStatusImage = document.querySelector('#like-status');
    const notLikeStatusImage = document.querySelector('#not-like-status');

    // 좋아요 상태 초기화
    initializeLikeStatus();

    // 좋아요 상태 클릭 시 toggleLike 함수 호출
    if (likeStatusImage) {
        likeStatusImage.addEventListener('click', toggleLike);
    }

    if (notLikeStatusImage) {
        notLikeStatusImage.addEventListener('click', toggleLike);
    }

    var socket = new SockJS('/recipe-detail');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // Subscribe to a topic (example: /topic/recipes)
        stompClient.subscribe('/topic/recipes', function (message) {
            var body = JSON.parse(message.body);

            // Handle the incoming message and update the DOM
            if (body.type === 'RECIPE_UPDATE') {
                // Update recipe details on the page
                updateRecipeDetails(body.recipe);
            }
        });
    });

    function initializeLikeStatus() {
        // 여기서 서버에서 현재 좋아요 상태를 가져오고, UI를 초기화합니다.
        fetch(`/api/likes/status?recipeId=${recipeId}&userId=${userId}`)
            .then(response => response.json())
            .then(data => {
                const likeImage = document.querySelector('#like-status');
                const notLikeImage = document.querySelector('#not-like-status');

                if (likeImage && notLikeImage) {
                    if (data.userLiked) {
                        likeImage.src = '/images/liked.png';
                        likeImage.setAttribute('data-user-liked', 'true');
                        notLikeImage.style.display = 'none';
                    } else {
                        likeImage.src = '/images/not-liked.png';
                        likeImage.setAttribute('data-user-liked', 'false');
                        notLikeImage.style.display = 'block';
                    }
                }
            })
            .catch(error => console.error('Error:', error));
    }

    function toggleLike() {
        const likeImage = document.querySelector('#like-status');
        const notLikeImage = document.querySelector('#not-like-status');
        const isLiked = likeImage && likeImage.getAttribute('data-user-liked') === 'true';

        // 좋아요 상태에 따라 요청을 다르게 보냅니다.
        const method = isLiked ? 'DELETE' : 'POST';  // 이미 좋아요 상태면 DELETE, 아니면 POST
        console.log(method)
        fetch(`/api/likes`, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userId: userId,
                recipeId: recipeId,
            })
        })
            .then(response => response.json())
            .then(data => {
                // 이미지와 likeCount를 업데이트하는 로직
                if (likeImage) {
                    if (data.userLiked) {
                        likeImage.src = '/images/liked.png';
                        likeImage.setAttribute('data-user-liked', 'true');
                        if (notLikeImage) {
                            notLikeImage.style.display = 'none'; // Hide not liked
                        }
                    } else {
                        likeImage.src = '/images/not-liked.png';
                        likeImage.setAttribute('data-user-liked', 'false');
                        if (notLikeImage) {
                            notLikeImage.style.display = 'block'; // Show not liked
                        }
                    }
                }
            })
            .catch(error => console.error('Error:', error));
    }

    function updateRecipeDetails(recipe) {
        // Update the recipe details on the page
        document.querySelector('h2').textContent = recipe.name;
        document.querySelector('img').src = recipe.recipeImageUrl;
        // Add more fields as needed
    }
});
