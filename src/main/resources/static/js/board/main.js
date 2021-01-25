(() => {
    const sectionInfo = [
        {
            sectionHeightNum: 5,
            objs : {
                container: document.querySelector('#section-content'),
                boardImgs: document.querySelectorAll('.board-img')
            }
        }
    ]

    function setLayout() {
        document.querySelector(".container").style.minHeight = `${window.innerHeight}px`;
    }

    document.addEventListener('load', () => {
        setLayout();
    });
})();