$(document).ready(function () {

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

    var $searchInput = $('#term'); // 검색어 입력창

    // 검색어 입력 상태 감지
    $searchInput.on('input', function () {
        var term = $(this).val().trim();
        console.log(term); // 디버깅용
        if (term.length > 0) {
            console.log(">0")
            $('#searchedListsContainer').hide();
            $('#searchResults').show();
            fetchAutocompleteResults(term);
        } else {
            console.log("0")
            $('#searchResults').html('');
            $('#searchResults').hide();
            $('#searchedListsContainer').show(); // 검색어가 비어있으면 숨김
        }
    });

    // 포커스 이벤트 처리
    $searchInput.on('focus', function () {
        var term = $(this).val().trim();
        if (term.length === 0) {
            $('#searchResults').html('');
            $('#searchResults').hide();
            $('#searchedListsContainer').show(); // 검색어가 비어있으면 숨김
        }
    });

    // 포커스 아웃 이벤트 처리
    $searchInput.on('blur', function () {
        var term = $(this).val().trim();
        if (term.length === 0) {
            $('#searchedListsContainer').hide();
            $('#searchResults').hide();
        }
    });

    // 자동완성 요청
    function fetchAutocompleteResults(term) {
        $.ajax({
            type: "GET",
            url: "/api/search/autocomplete",
            data: {prefix: term},
            success: function (data) {
                console.log("data")
                var html = generateAutocompleteHTML(data);
                $('#searchResults').html(html);
                resultsCount = data.length; // 결과 개수 업데이트
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

    // Function to generate HTML for autocomplete results
    function generateAutocompleteHTML(data) {
        var html = '<ul class="list-group autocomplete-list">';
        $.each(data, function (index, item) {
            let name = item.name;
            let imageUrl = item.imageUrl;
            let recipeId = item.recipeId;

            html += '<li class="list-group-item autocomplete-item">';
            html += '<div class="autocomplete-details">' + name + '</div>';
            // html += '<img src="' + imageUrl + '" alt="' + name + '" style="width: 60px; height: 60px; margin-left: 10px;">';
            html += '<img src="' + imageUrl + '" alt="' + name + '" class="autocomplete-image">';

            html += '<p class="card-text" style="display: none;">Recipe ID: ' + recipeId + '</p>';
            html += '</li>';
        });
        html += '</ul>';
        return html;
    }

    // 인기 검색어 요청
    function fetchPopularSearches() {
        $.ajax({
            type: "GET",
            url: "/api/ranking/terms",
            success: function (data) {
                displayPopularSearches(data);
                rollRanking();
            },
            error: function (e) {
                console.error("Error fetching popular searches: ", e);
            }
        });
    }

    // 인기 검색어 결과 프론트 코드 작성 함수
    function displayPopularSearches(data) {
        var popularSearches = $('#popularSearchesList');
        popularSearches.empty();
        $.each(data, function (index, item) {
            var rank = index + 1;
            var listItem = '<li id="rank_' + rank + '" class="list-group-item">' + rank + '. ' + item.term + '</li>';
            popularSearches.append(listItem);
        });
    }

    // Function to implement rolling ranks
    function rollRanking() {
        var currentRank = 1;
        var totalRanks = $('#popularSearches').children().length;

        setInterval(function () {
            $('#rank_' + currentRank).fadeOut('fast', function () {
                currentRank++;
                if (currentRank > totalRanks) {
                    currentRank = 1;
                }
                $('#rank_' + currentRank).appendTo('#popularSearches').fadeIn('fast');
            });
        }, 3000); // 3-second interval
    }

    // [html] 최근 검색어 데이터 추가
    function addRecentSearch(term) {
        var listItem = '<li>' + term + '</li>';
        $('#recentSearchesList').append(listItem);
    }

    // [html] 인기 검색어 데이터 추가
    function addPopularSearch(term, count) {
        var listItem = '<li>' + term + ' (' + count + ')</li>';
        $('#popularSearchesList').append(listItem);
    }

    // 서버에서 최근 검색어 데이터 가져오기
    function fetchRecentSearches() {
        $.ajax({
            type: "GET",
            url: "/api/search/recent",
            success: function (data) {
                $('#recentSearchesList').empty(); // 기존 데이터 삭제
                $.each(data, function (index, item) {
                    addRecentSearch(item.search, item.timestamp);
                });
            },
            error: function (e) {
                console.error("Error fetching recent searches: ", e);
            }
        });
    }

    // 서버에서 인기 검색어 데이터 가져오기
    function fetchPopularSearches() {
        $.ajax({
            type: "GET",
            url: "/api/search/popular",
            success: function (data) {
                $('#popularSearchesList').empty(); // 기존 데이터 삭제
                displayPopularSearches(data)
            },
            error: function (e) {
                console.error("Error fetching popular searches: ", e);
            }
        });
    }

    // 초기에 최근 검색어와 인기 검색어 데이터 가져오기
    fetchRecentSearches();
    fetchPopularSearches();

    // 일정 시간마다 데이터 갱신
    setInterval(function () {
        fetchRecentSearches();
        fetchPopularSearches();
    }, 300000); // 5분 간격으로 데이터 갱신
    // Initial fetch of popular searches on page load
    fetchPopularSearches();

    // Fetch popular searches every 5 minutes
    setInterval(fetchPopularSearches, 300000); // 5-minute interval
});
