(() => {
    let boardIdentityKey = 0;
    let beforeBoardIdentityKey = 0;
    let beforeBoardElement = null;
    let updating = false;

    function textarea_resize(target) {
        target.style.height = `1px`;
        target.style.height = `${(12 + target.scrollHeight)}px`;
    }

    function modalOpen(target) {
        if (updating) {
            beforeBoardIdentityKey = target.dataset.identity;
            beforeBoardElement = target;
            modalConfirmOpen();
            return;
        }
        document.body.style.overflowY = 'hidden';
        document.querySelector(`#modal`).style.display = `block`;

        let element = document.querySelector(`#modal-content`);
        let elementHeight = element.offsetHeight;
        let windowHeight = window.innerHeight;
        element.style.top = `${((parseInt(windowHeight) - parseInt(elementHeight)) / 2)}px`;
        boardIdentityKey = target.dataset.identity;
    }

    function modalClose() {
        document.body.style.overflowY = 'auto';
        document.querySelector(`#modal`).style.display = `none`;
    }

    function modalConfirmOpen() {
        modalClose();
        document.body.style.overflowY = 'hidden';
        document.querySelector(`#modal-confirm`).style.display = `block`;

        let element = document.querySelector(`#modal-confirm-content`);
        let elementHeight = element.offsetHeight;
        let windowHeight = window.innerHeight;
        element.style.top = `${((parseInt(windowHeight) - parseInt(elementHeight)) / 2)}px`;
    }

    function modalConfirmClose() {
        document.body.style.overflowY = 'auto';
        document.querySelector(`#modal-confirm`).style.display = `none`;
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

    function update() {
        let title = document.querySelector(`#board-identity-${boardIdentityKey} .board-update-title-input`).value;
        let content = document.querySelector(`#board-identity-${boardIdentityKey} .board-update-content-input`).value;
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
            method: `put`,
            contentType: `application/json`,
            data: JSON.stringify({
                seq: boardIdentityKey,
                title: title,
                content: content,
                status: `REGISTERED`
            }),
            success: function(response) {
                updating = false;
                document.querySelector(`#board-identity-${boardIdentityKey} .board-title-strong`).innerText = response.data.title;
                document.querySelector(`#board-identity-${boardIdentityKey} .board-content-strong`).innerText = response.data.content;
                update_cancel();
            },
            error: function(error) {
                console.log(error);
                alert(`게시글 수정 실패`);
            }
        });
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
                if (response.data.length < 1) {
                    let createEl = document.createElement("div");
                    createEl.className = `board-not-content`;
                    createEl.innerText =
                        `등록된 일상이 없습니다
                    자신의 일상을 등록해 공유해보세요`;
                    el.appendChild(createEl);
                    return;
                }

                for (let i = 0; i < response.data.length; i++) {
                    html =
                    `<div class="board-content-title-field">
                        <div class="board-content-title">
                            <div class="board-title-strong">${response.data[i].title}</div>
                            <div class="board-update-title-field">
                                <input class="board-update-title-input" maxlength="14" placeholder="글 제목을 입력해주세요" value="${response.data[i].title}">
                            </div>
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
                        <pre class="board-content-strong">${response.data[i].content}</pre>
                        <div class="board-update-content-field">
                            <textarea class="board-update-content-input board-text-area" placeholder="글 내용을 입력해주세요">${response.data[i].content}</textarea>
                        </div>
                    </div>
                    <div class="board-content-time-field">
                        <div class="board-content-time">${timeForToday(response.data[i].created)}</div>
                        <div class="board-update-content-btn-field">
                            <button class="board-update-content-btn">게시하기</button>
                        </div>
                    </div>`;

                    let createEl = document.createElement("div");
                    createEl.className = `board-content`;
                    createEl.id = `board-identity-${response.data[i].seq}`;
                    createEl.innerHTML = html;
                    createEl.querySelector(`.circle-option`).addEventListener(`click`, (event) => {
                        modalOpen(event.currentTarget);
                    });
                    createEl.querySelector(`.board-text-area`).addEventListener(`keydown`, (event) => {
                        textarea_resize(event.target);
                    });
                    createEl.querySelector(`.board-text-area`).addEventListener(`keyup`, (event) => {
                        textarea_resize(event.target);
                    });
                    createEl.querySelector(`.board-update-content-btn`).addEventListener(`click`, (event) => {
                        update();
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
                        <div class="board-title-strong">${response.data.title}</div>
                        <div class="board-update-title-field">
                            <input class="board-update-title-input" maxlength="14" value="${response.data.title}">
                        </div>
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
                    <pre class="board-content-strong">${response.data.content}</pre>
                    <div class="board-update-content-field">
                        <textarea class="board-update-content-input board-text-area">${response.data.content}</textarea>
                    </div>
                </div>
                <div class="board-content-time-field">
                    <div class="board-content-time">${timeForToday(response.data.created)}</div>
                    <div class="board-update-content-btn-field">
                        <button class="board-update-content-btn">게시하기</button>
                    </div>
                </div>`;

                let el = document.querySelector(`.section-board`);
                let createEl = document.createElement("div");
                createEl.className = `board-content`;
                createEl.id = `board-identity-${response.data.seq}`;
                createEl.innerHTML = html;
                createEl.querySelector(`.circle-option`).addEventListener(`click`, (event) => {
                    modalOpen(event.currentTarget);
                });
                createEl.querySelector(`.board-text-area`).addEventListener(`keydown`, (event) => {
                    textarea_resize(event.target);
                });
                createEl.querySelector(`.board-text-area`).addEventListener(`keyup`, (event) => {
                    textarea_resize(event.target);
                });
                createEl.querySelector(`.board-update-content-btn`).addEventListener(`click`, (event) => {
                    update();
                });

                el.insertBefore(createEl, el.firstChild);

                let boardCnt = document.querySelectorAll(`.board-not-content`).length;
                if (boardCnt > 0) {
                    document.querySelector(`.board-not-content`).remove();
                }

                textarea_resize(document.querySelector(`#board-create-content`));
            },
            error: function(error) {
                console.log(error);
                alert(`게시글 등록 실패`);
            }
        });
    }

    function board_delete() {
        $.ajax({
            url: `/board/${boardIdentityKey}`,
            dataType: `json`,
            method: `delete`,
            success: function(response) {
                if (response === null || response.resultCode !== `OK`) {
                    alert(`게시글 삭제 실패`);
                    return;
                }

                document.querySelector(`#board-identity-${response.data.seq}`).remove();
                modalClose();

                let boardCnt = document.querySelectorAll(`.board-content`).length;
                if (boardCnt< 1) {
                    let createEl = document.createElement("div");
                    createEl.className = `board-not-content`;
                    createEl.innerText =
                        `등록된 일상이 없습니다
                    자신의 일상을 등록해 공유해보세요`;
                    document.querySelector(`.section-board`).appendChild(createEl);
                }
            },
            error: function(error) {
                console.log(error);
                alert(`게시글 삭제 실패`);
            }
        });
    }

    function update_change() {
        updating = true;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-title-strong`).style.display = `none`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-title-field`).style.display = `block`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-content-strong`).style.display = `none`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-content-field`).style.display = `block`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-content-btn-field`).style.display = 'block';
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-title-input`).focus();
    }

    function update_cancel() {
        updating = false;
        let strongTitle = document.querySelector(`#board-identity-${boardIdentityKey} .board-title-strong`).innerText;
        let strongContent = document.querySelector(`#board-identity-${boardIdentityKey} .board-content-strong`).innerText;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-title-input`).value = strongTitle;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-content-input`).value = strongContent;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-title-strong`).style.display = `block`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-title-field`).style.display = `none`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-content-strong`).style.display = `block`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-content-field`).style.display = `none`;
        document.querySelector(`#board-identity-${boardIdentityKey} .board-update-content-btn-field`).style.display = 'none';
    }

    $().ready(function () {
        list();
        document.querySelector('#board-create-btn').addEventListener('click', create);
        document.querySelector('#modal-layer').addEventListener('click', modalClose);
        document.querySelector('#modal-confirm-layer').addEventListener('click', modalConfirmClose);
        window.addEventListener('resize', () => {
            let element = document.querySelector(`#modal-content`);
            let element_confirm = document.querySelector(`#modal-confirm-content`);
            let elementHeight = element.offsetHeight;
            let element_confirmHeight = element_confirm.offsetHeight;
            let windowHeight = window.innerHeight;
            element.style.top = `${((parseInt(windowHeight) - parseInt(elementHeight)) / 2)}px`;
            element_confirm.style.top = `${((parseInt(windowHeight) - parseInt(element_confirmHeight)) / 2)}px`;
        })

        document.querySelector(`.board-update-btn-field`).addEventListener(`click`, (event) => {
            update_change();
            modalClose();
            event.stopPropagation();
        });
        document.querySelector(`.board-delete-btn-field`).addEventListener(`click`, (event) => {
            board_delete(event.target);
            event.stopPropagation();
        });
        document.querySelector(`.modal-cancel-field`).addEventListener(`click`, (event) => {
            modalClose();
            event.stopPropagation();
        });

        document.querySelector(`#board-create-content`).addEventListener(`keydown`, (event) => {
            textarea_resize(event.target);
        });

        document.querySelector(`#board-create-content`).addEventListener(`keyup`, (event) => {
            textarea_resize(event.target);
        });

        document.querySelector(`.modal-confirm-y-field`).addEventListener('click', (event) => {
            update_cancel()
            modalConfirmClose();
            modalOpen(beforeBoardElement);
            event.stopPropagation();
        });

        document.querySelector(`.modal-confirm-n-field`).addEventListener('click', (event) => {
            modalConfirmClose();
            event.stopPropagation();
        })
    });
})()