(() => {
    const sectionInfo = [
        {
            sectionHeightNum: 5,
            objs : {
                container: document.querySelector('#section-content'),
            }
        }
    ]

    function setLayout() {
        document.querySelector(".container").style.minHeight = `${window.innerHeight}px`;
    }

    window.addEventListener('load', () => {
        setLayout();
    });
})();