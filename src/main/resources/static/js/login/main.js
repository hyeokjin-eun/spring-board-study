(() => {
    function setLayout() {
        document.querySelector(".container").style.minHeight = `${window.innerHeight}px`;
        if (window.innerWidth < 1024) {
            let loginContentFieldHeight = document.querySelector(".login-content-field").offsetHeight;
            document.querySelector(".login-section").style.minWidth = `${window.innerWidth}px`;
            document.querySelector(".login-content").style.minWidth = `${window.innerWidth}px`;
            document.querySelector(".login-content").style.minHeight = `${window.innerHeight}px`;
            document.querySelector(".login-content-field").style.paddingTop = `${(window.innerHeight - loginContentFieldHeight) / 2}px`;
        } else {
            document.querySelector(".login-section").style.minWidth = `500px`;
            document.querySelector(".login-content").style.minWidth = `500px`;
            document.querySelector(".login-content").style.minHeight = `100%`;
            document.querySelector(".login-content-field").style.paddingTop = `0`;
        }
    }

    window.addEventListener('load', () => {
        setLayout();
    });

    window.addEventListener('resize', () => {
        setLayout();
    })
})();