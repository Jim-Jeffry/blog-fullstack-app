document.addEventListener('DOMContentLoaded', function () {
  fetch('/api/posts')
    .then(res => res.json())
    .then(data => {
      const container = document.getElementById('posts-container');
      data.forEach(post => {
        const div = document.createElement('div');
        div.innerHTML = `<h3>${post.title}</h3><p>${post.content}</p>`;
        container.appendChild(div);
      });
    });
});
