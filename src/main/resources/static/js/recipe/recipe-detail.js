document.addEventListener("DOMContentLoaded", function () {
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

    function updateRecipeDetails(recipe) {
        // Update the recipe details on the page
        document.querySelector('h2').textContent = recipe.name;
        document.querySelector('img').src = recipe.recipeImageUrl;
        // Add more fields as needed
    }
});