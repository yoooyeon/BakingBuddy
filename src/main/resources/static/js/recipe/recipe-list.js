// 타임리프 사용 시 [[${...}]]

$(document).ready(function () {
<<<<<<< HEAD
=======
    // 토큰을 로컬 스토리지에서 가져오기
    const accessToken = localStorage.getItem('accessToken');

    // 모든 AJAX 요청 전에 Authorization 헤더에 토큰을 추가
    $.ajaxSetup({
        beforeSend: function(xhr) {
            if (accessToken) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + accessToken);
            }
        }
    });
>>>>>>> jwt

    // 레시피 클릭 시 작동하는 함수
    $('.recipe-card').on('click', function () {
        var recipeUrl = $(this).data('url');
        if (recipeUrl) {
            window.location.href = recipeUrl;
        }
    });


    // 방향키 관련 변수
    var selectedIndex = -1; // 선택된 인덱스
    var resultsCount = 0; // 결과 개수

    // 검색어 자동 완성 감지 및 요청
    $('#term').on('input', function () {
        var term = $(this).val().trim();
        if (term.length > 0) {
            fetchAutocompleteResults(term);
        } else {
            $('#searchResults').html('');
        }
    });
    localStorage.getItem('token')

    // 자동완성 요청
    function fetchAutocompleteResults(term) {
        $.ajax({
            type: "GET",
            url: "/api/search/autocomplete",
            data: {prefix: term},
            success: function (data) {
                var html = generateAutocompleteHTML(data);
                $('#searchResults').html(html);
            },
            error: function (e) {
                console.error("Error fetching autocomplete results: ", e);
            }
        });
    }

    // 엔터키와 방향키 이벤트 처리
    $('#term').on('keydown', function (e) {
        if (e.keyCode === 13) { // 엔터키
            e.preventDefault(); // 기본 동작 방지 (폼 제출 방지)
            if (selectedIndex > -1 && selectedIndex < resultsCount) {
                var selectedText = $('#searchResults .list-group-item').eq(selectedIndex).text();
                $('#term').val(selectedText); // 선택된 결과를 입력창에 채움
                $('#searchResults').html(''); // 자동 완성 결과 창 비우기
                // 여기에 검색 실행하는 코드 추가
                $('#searchForm').submit(); // 검색 폼 제출
            }
        } else if (e.keyCode === 38) { // 위 방향키
            e.preventDefault(); // 기본 동작 방지 (페이지 스크롤 방지)
            if (selectedIndex > 0) {
                selectedIndex--; // 인덱스 감소
                highlightSelectedResult();
            }
        } else if (e.keyCode === 40) { // 아래 방향키
            e.preventDefault(); // 기본 동작 방지 (페이지 스크롤 방지)
            if (selectedIndex < resultsCount - 1) {
                selectedIndex++; // 인덱스 증가
                highlightSelectedResult();
            }
        }
    });

    // 선택된 결과 강조
    function highlightSelectedResult() {
        $('#searchResults .list-group-item').removeClass('active');
        $('#searchResults .list-group-item').eq(selectedIndex).addClass('active');
    }

    // 이벤트 리스너 추가
    document.addEventListener('DOMContentLoaded', function () {
        const termInput = document.getElementById('term');
        termInput.addEventListener('input', function () {
            fetchAutocompleteResults(termInput.value);
        });
    });

    // Function to generate HTML for autocomplete results
    function generateAutocompleteHTML(data) {
        var html = '<ul class="list-group">';
        $.each(data, function (index, item) {
            html += '<li class="list-group-item">' + item + '</li>';
        });
        html += '</ul>';
        return html;
    }

});
