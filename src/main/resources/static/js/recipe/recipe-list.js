// 타임리프 사용 시 [[${...}]]
$(document).ready(function () {
    let currentPage = 0;
    let totalPages = /*[[${totalPages}]]*/ 0;
    const loading = document.getElementById('loading');

    function loadMoreRecipes() {
        if (currentPage >= totalPages) {
            loading.style.display = 'none';
            return;
        }

        currentPage++;
        loading.style.display = 'block';

        $.ajax({
            url: `/api/recipes?page=${currentPage}`,
            method: 'GET',
            success: function(data) {
                $('#recipe-container').append(data);
                loading.style.display = 'none';
            },
            error: function() {
                loading.style.display = 'none';
            }
        });
    }

    $(window).scroll(function() {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            loadMoreRecipes();
        }
    });

    // Initial load
    loadMoreRecipes();
});
