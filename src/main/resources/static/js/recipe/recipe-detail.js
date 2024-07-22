let recipeId, userId;

document.addEventListener("DOMContentLoaded", function () {
    // 초기 값 세팅
    recipeId = document.getElementById('recipe-id').value;
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

        stompClient.subscribe('/topic/recipes', function (message) {
            var body = JSON.parse(message.body);
            console.log("body=", body);
            updateRecipeDetails(body);
        });
    });

    function updateRecipeDetails(data) {
        console.log("updateRecipeDetails", data)
        // 서버로부터 받은 데이터에서 좋아요 상태와 좋아요 수를 사용하여 UI를 업데이트합니다.
        const likeImage = document.querySelector('#like-status');
        const notLikeImage = document.querySelector('#not-like-status');
        console.log(likeImage && notLikeImage)
        if (data.userLiked) {
            console.log(data.userLiked)
            likeImage.src = '/images/liked.png';
            likeImage.setAttribute('data-user-liked', 'true');
            notLikeImage.style.display = 'none'; // Hide not liked
        } else {
            likeImage.src = '/images/not-liked.png';
            likeImage.setAttribute('data-user-liked', 'false');
            notLikeImage.style.display = 'block'; // Show not liked
        }

        // 좋아요 수를 업데이트합니다.
        document.querySelector('.like-count').textContent = data.likeCount;
    }

    function initializeLikeStatus() {
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

        const method = isLiked ? 'DELETE' : 'POST';  // 이미 좋아요 상태면 DELETE, 아니면 POST
        console.log(method);
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
});
