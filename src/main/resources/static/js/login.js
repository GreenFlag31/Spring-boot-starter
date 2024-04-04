const connectionBtn = document.querySelectorAll('.connection-modalities button');
const signInContainer = document.querySelector('.sign-in-container');
const registerContainer = document.querySelector('.register-container');
const line = document.querySelector('.line');
const signInWidth = signInContainer.clientWidth;
const signInHeight = signInContainer.clientHeight;
registerContainer.style.minWidth = signInWidth + 'px';
// registerContainer.style.minHeight = signInHeight + 'px';

connectionBtn.forEach((btn) => {
  btn.addEventListener('click', (event) => {
    const target = event.currentTarget.innerText;
    if (target === 'Register') {
      signInContainer.style.display = 'none';
      registerContainer.style.display = 'block';
      line.style.left = '50%';
    } else {
      signInContainer.style.display = 'block';
      registerContainer.style.display = 'none';
      line.style.left = '0';
    }
  });
});
