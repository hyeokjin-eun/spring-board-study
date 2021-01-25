(() => {
    let yOffset = 0;
    let preScrollHeight = 0;
    let currentScene = 0;
    let enterNewScene = false;

    const sceneInfo = [
        {
            type: 'sticky',
            heightNum: 5,
            scrollHeight: 0,
            objs: {
                container: document.querySelector("#scroll-section-0"),
                messageA: document.querySelector("#scroll-section-0 .main-message.a"),
                messageB: document.querySelector("#scroll-section-0 .main-message.b"),
                messageC: document.querySelector("#scroll-section-0 .main-message.c"),
                canvas: document.querySelector('#video-canvas-0'),
                context: document.querySelector('#video-canvas-0').getContext('2d'),
                videoImages: []
            },
            values : {
                videoImageCount: 200,
                imageSequence: [0, 200],
                messageA_opacity_in: [0, 1, {start: 0.1, end: 0.2}],
                messageA_opacity_out: [1, 0, {start: 0.3, end: 0.35}],
                messageA_translateY_in: [20, 0, {start: 0.1, end: 0.2}],
                messageA_translateY_out: [0, -20, {start: 0.3, end: 0.35}],
                messageB_opacity_in: [0, 1, {start: 0.4, end: 0.5}],
                messageB_opacity_out: [1, 0, {start: 0.6, end: 0.65}],
                messageB_translateY_in: [20, 0, {start: 0.4, end: 0.5}],
                messageB_translateY_out: [0, -20, {start: 0.6, end: 0.65}],
                messageC_opacity_in: [0, 1, {start: 0.7, end: 0.8}],
                messageC_opacity_out: [1, 0, {start: 0.9, end: 0.95}],
                messageC_translateY_in: [20, 0, {start: 0.7, end: 0.8}],
                messageC_translateY_out: [0, -20, {start: 0.9, end: 0.95}]
            }
        }
    ]

    function setCanvasImages() {
        let imgElem;
        for (let i = 0; i < sceneInfo[0].values.videoImageCount; i++) {
            imgElem = new Image();
            imgElem.src = `../../img/home/video/Alone${i + 1}.jpg`;
            sceneInfo[0].objs.videoImages.push(imgElem);
        }
    }

    setCanvasImages();

    function setLayout() {
        for (let i = 0; i < sceneInfo.length; i++) {
            sceneInfo[i].scrollHeight = sceneInfo[i].heightNum * window.innerHeight;
            sceneInfo[i].objs.container.style.height = `${sceneInfo[i].scrollHeight}px`;
        }

        yOffset = window.pageYOffset;
        let totalScrollHeight = 0;
        for (let i = 0; i < sceneInfo.length; i++) {
            totalScrollHeight = sceneInfo[i].scrollHeight;
            if (totalScrollHeight >= yOffset) {
                currentScene = i;
                break;
            }
        }

        document.body.setAttribute('id', `show-scene-${currentScene}`);

        const widthRatio0 = window.innerWidth / sceneInfo[0].objs.canvas.width;
        const heightRatio0 = window.innerHeight / sceneInfo[0].objs.canvas.height;
        let canvasScaleRatio0;
        if (widthRatio0 <= heightRatio0) {
            canvasScaleRatio0 = heightRatio0;
        } else {
            canvasScaleRatio0 = widthRatio0;
        }

        sceneInfo[0].objs.canvas.style.transform = `translate3d(-50%, -50%, 0) scale(${canvasScaleRatio0})`;
    }

    function calcValues(values, currentYOffset) {
        let result;
        const scrollHeight = sceneInfo[currentScene].scrollHeight;
        const scrollRatio = currentYOffset / scrollHeight;
        if (values.length === 3) {
            const partScrollStart = values[2].start * scrollHeight;
            const partScrollEnd = values[2].end * scrollHeight;
            const partScrollHeight = partScrollEnd - partScrollStart;
            if (currentYOffset >= partScrollStart && currentYOffset <= partScrollEnd) {
                result = (currentYOffset - partScrollStart) / partScrollHeight * (values[1] - values[0]) + values[0];
            } else if (currentYOffset < partScrollStart) {
                result = values[0];
            } else if (currentYOffset > partScrollEnd) {
                result = values[1];
            }
        } else {
            result = scrollRatio * (values[1] - values[0]) + values[0];
        }

        return result;
    }

    function playAnimation() {
        const objs = sceneInfo[currentScene].objs;
        const values = sceneInfo[currentScene].values;
        const currentYOffset = yOffset - preScrollHeight;
        const scrollHeight = sceneInfo[currentScene].scrollHeight;
        const scrollRatio = currentYOffset / scrollHeight

        switch (currentScene) {
            case 0:
                let sequence = Math.round(calcValues(values.imageSequence, currentYOffset));
                objs.context.drawImage(objs.videoImages[sequence], 0, 0);

                if (scrollRatio <= 0.25) {
                    objs.messageA.style.opacity = calcValues(values.messageA_opacity_in, currentYOffset);
                    objs.messageA.style.transform = `translate3d(0, ${calcValues(values.messageA_translateY_in, currentYOffset)}%, 0)`;
                } else {
                    objs.messageA.style.opacity = calcValues(values.messageA_opacity_out, currentYOffset);
                    objs.messageA.style.transform = `translate3d(0, ${calcValues(values.messageA_translateY_out, currentYOffset)}%, 0)`;
                }

                if (scrollRatio <= 0.55) {
                    objs.messageB.style.opacity = calcValues(values.messageB_opacity_in, currentYOffset);
                    objs.messageB.style.transform = `translate3d(0, ${calcValues(values.messageB_translateY_in, currentYOffset)}%, 0)`;
                } else {
                    objs.messageB.style.opacity = calcValues(values.messageB_opacity_out, currentYOffset);
                    objs.messageB.style.transform = `translate3d(0, ${calcValues(values.messageB_translateY_out, currentYOffset)}%, 0)`;
                }

                if (scrollRatio <= 0.85) {
                    objs.messageC.style.opacity = calcValues(values.messageC_opacity_in, currentYOffset);
                    objs.messageC.style.transform = `translate3d(0, ${calcValues(values.messageC_translateY_in, currentYOffset)}%, 0)`;
                } else {
                    objs.messageC.style.opacity = calcValues(values.messageC_opacity_out, currentYOffset);
                    objs.messageC.style.transform = `translate3d(0, ${calcValues(values.messageC_translateY_out, currentYOffset)}%, 0)`;
                }

                break;
        }
    }

    function scrollLoop() {
        preScrollHeight = 0;
        enterNewScene = false;

        for (let i = 0; i < currentScene; i++) {
            preScrollHeight += sceneInfo[i].scrollHeight
        }

        if (yOffset > preScrollHeight + sceneInfo[currentScene].scrollHeight) {
            enterNewScene = true;
            currentScene++;
            document.body.setAttribute('id', `show-scene-${currentScene}`);
        }

        if (yOffset < preScrollHeight) {
            enterNewScene = true;
            if (currentScene === 0) return;
            currentScene--;
            document.body.setAttribute('id', `show-scene-${currentScene}`);
        }

        if (enterNewScene) return;

        playAnimation();
    }

    window.addEventListener('load', () => {
        sceneInfo[0].objs.context.drawImage(sceneInfo[0].objs.videoImages[0], 0, 0);
        setLayout();
    });
    window.addEventListener('resize', setLayout);
    window.addEventListener('scroll', () => {
        yOffset = window.pageYOffset;
        scrollLoop();
    });


})();