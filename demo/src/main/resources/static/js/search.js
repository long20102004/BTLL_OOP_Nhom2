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
                postElement.addEventListener('click', function () {
                    window.location.href = 'http://localhost:8080/questions/' + post.id;
                });
                resultsContainer.appendChild(postElement);
            });
        } else {
            resultsContainer.style.display = 'none';
        }
    };

    ws.onerror = function (error) {
        console.error('WebSocket error: ', error);
    };
}

document.getElementById('search-input').addEventListener('input', function () {
    const query = this.value;
    if (ws.readyState === WebSocket.OPEN) {
        ws.send(JSON.stringify({ query: query }));
    } else if (ws.readyState === WebSocket.CLOSING || ws.readyState === WebSocket.CLOSED) {
        console.log('WebSocket is in CLOSING or CLOSED state, attempting to reconnect...');
        connect();
    }
});

connect();