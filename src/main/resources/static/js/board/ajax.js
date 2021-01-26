(() => {
    const boardIdentityKey = 0;

    function modalOpen(target) {
        document.body.style.overflowY = 'hidden';
        document.querySelector(`#modal`).style.display = `block`;

        let element = document.querySelector(`#modal-content`);
        let elementHeight = element.offsetHeight;
        let windowHeight = window.innerHeight;
        let scrollHeight = window.pageYOffset;
        console.log(`elementHeight : ${elementHeight}, windowHeight : ${windowHeight}, scrollHeight : ${scrollHeight}`);
        element.style.top = `${((parseInt(windowHeight) - parseInt(elementHeight)) / 2)}px`;
        console.log(target.dataset.identity);
    }

    function modalClose() {
        document.body.style.overflowY = 'auto';
        document.querySelector(`#modal`).style.display = `none`;
    }

    function timeForToday(value) {
        const today = new Date();
        const timeValue = new Date(value);

        const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60);
        if (betweenTime < 1) {
            return '방금전';
        }

        if (betweenTime < 60) {
            return `${betweenTime}분전`;
        }

        const betweenTimeHour = Math.floor(betweenTime / 60);
        if (betweenTimeHour < 24) {
            return `${betweenTimeHour}시간전`;
        }

        const betweenTimeDay = Math.floor(betweenTimeHour / 24);
        if (betweenTimeDay < 365) {
            return `${betweenTimeDay}일전`;
        }

        return `${Math.floor(betweenTimeDay / 365)}년전`;
    }

    function list() {
        $.ajax({
            url: `/board`,
            dataType: `json`,
            method: `get`,
            success: function(response) {
                let html = ``;
                if (response === null || response.data === null || response.data === 'undefined') {
                    // TODO list length == 0 일때 list 에 일상을 공유해보세요 문구 출력
                    html += ``;
                    document.querySelector(`.section-board`).innerHTML = html;
                    return;
                }

                let el = document.querySelector(`.section-board`);
                for (let i = 0; i < response.data.length; i++) {
                    html =
                    `<div class="board-content-title-field">
                        <div class="board-content-title">
                            <strong>${response.data[i].title}</strong>
                        </div>
                        <div class="circle-option" data-identity="${response.data[i].seq}">
                            <svg height="16" width="16" viewBox="0 0 48 48">
                                <circle cx="8" cy="24" r="4.5"/>
                                <circle cx="24" cy="24" r="4.5"/>
                                <circle cx="40" cy="24" r="4.5"/>
                            </svg>
                        </div>
                    </div>
                    <div class="board-content-img">
                        <img class="board-img" src="/img/board/board-default.jpg"/>
                    </div>
                    <div class="board-content-content">
                        ${response.data[i].content}
                    </div>
                    <div class="board-content-time">
                        ${timeForToday(response.data[i].created)}
                    </div>`;

                    let createEl = document.createElement("div");
                    createEl.className = `board-content`;
                    createEl.innerHTML = html;
                    createEl.querySelector(`.circle-option`).addEventListener(`click`, (event) => {
                        modalOpen(event.currentTarget);
                    });
                    el.appendChild(createEl);
                }
            },
            error: function(error) {
                console.log(error);
                alert(`게시글 조회 실패`);
            }
        });
    }

    function create() {
        let title = document.querySelector('#board-create-title').value;
        let content = document.querySelector('#board-create-content').value;
        if (title === null || title === 'undefined' || title === ``) {
            alert(`글 제목을 입력해주세요.`);
            return;
        }

        if (content === null || content === 'undefined' || content === ``) {
            alert(`글 내용을 입력해주세요.`);
            return;
        }

        $.ajax({
            url: `/board`,
            dataType: `json`,
            method: `post`,
            contentType: `application/json`,
            data: JSON.stringify({
                title: title,
                content: content
            }),
            success: function(response) {
                if (response === null || response.data === null || response.data === 'undefined') {
                    alert(`게시글 등록 실패`);
                    return;
                }

                document.querySelector('#board-create-title').value = '';
                document.querySelector('#board-create-content').value = '';

                let html =
                `<div class="board-content-title-field">
                    <div class="board-content-title">
                        <strong>${response.data.title}</strong>
                    </div>
                    <div class="circle-option" data-identity="${response.data.seq}">
                        <svg height="16" width="16" viewBox="0 0 48 48">
                            <circle cx="8" cy="24" r="4.5"/>
                            <circle cx="24" cy="24" r="4.5"/>
                            <circle cx="40" cy="24" r="4.5"/>
                        </svg>
                    </div>
                </div>
                <div class="board-content-img">
                    <img class="board-img" src="/img/board/board-default.jpg"/>
                </div>
                <div class="board-content-content">
                    ${response.data.content}
                </div>
                <div class="board-content-time">
                    ${timeForToday(response.data.created)}
                </div>`;

                let el = document.querySelector(`.section-board`);
                let createEl = document.createElement("div");
                createEl.className = `board-content`;
                createEl.innerHTML = html;
                createEl.querySelector(`.circle-option`).addEventListener(`click`, (event) => {
                    modalOpen(event.currentTarget);
                });
                el.insertBefore(createEl, el.firstChild);
            },
            error: function(error) {
                console.log(error);
                alert(`게시글 등록 실패`);
            }
        });
    }

    $().ready(function () {
        list();
        document.querySelector('#board-create-btn').addEventListener('click', create);
        document.querySelector('#modal-layer').addEventListener('click', modalClose);
        window.addEventListener('resize', () => {
            let element = document.querySelector(`#modal-content`);
            let elementHeight = element.offsetHeight;
            let windowHeight = window.innerHeight;
            element.style.top = `${((parseInt(windowHeight) - parseInt(elementHeight)) / 2)}px`;
        })

        document.querySelector(`.board-update-btn-field`).addEventListener(`click`, (event) => {
            console.log(event);
            alert(`test1`);

            event.stopPropagation();
        });
        document.querySelector(`.board-delete-btn-field`).addEventListener(`click`, (event) => {
            console.log(event);
            alert(`test2`);
            event.stopPropagation();
        });
        document.querySelector(`.modal-cancel-field`).addEventListener(`click`, (event) => {
            modalClose();
            event.stopPropagation();
        });
    });
})()