let ws;
let senderId = document.getElementById('sender-id').textContent;
function connect() {
    ws = new WebSocket('ws://' + window.location.host + '/ws/se?user=' + senderId);
    ws.onopen = function () {
        console.log('WebSocket connection opened');
    };

    ws.onmessage = function (event) {
        let data = JSON.parse(event.data).posts;
        const resultsContainer = document.getElementById('search-results');
        resultsContainer.innerHTML = '';
        if (data.length > 0) {
            resultsContainer.style.display = 'block';
            data.forEach(post => {
                const postElement = document.createElement('div');
                console.log(post);
                postElement.textContent = post.title;
                postElement.addEventListener('click', function (){
                    window.location.href = 'http://localhost:8080/questions/' + post.id;
                })
                resultsContainer.appendChild(postElement);
            });
        }
        else{
            resultsContainer.style.display = 'none';
        }
    };

    document.getElementById('search-input').addEventListener('input', function () {
        const query = this.value;
        ws.send(JSON.stringify({query: query}));
    });
}

connect();