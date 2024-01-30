console.log("board comment js in~~!!");
console.log(bnoVal);

document.getElementById('cmtPostBtn').addEventListener('click', ()=>{
    const cmtText = document.getElementById('cmtText');
    if(cmtText.value == null || cmtText.value == ''){
        alert('댓글을 입력해주세요.');
        cmtText.focus();
        return false;
    }else{
        let cmtData = {
            bno: bnoVal,
            writer: document.getElementById('cmtWriter').innerText,
            content: cmtText.value
        };

        postCommentToServer(cmtData).then(result =>{
            if(result == 1){
                alert('댓글 등록 성공~!!');
                cmtText.value = "";
            }
            spreadCommentList(bnoVal);
        })
    }
})

async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config = {
            method: 'post',
            headers: {
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtData)
        };

        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function getCommentListFromServer(bno, page){
    try {
        const resp = await fetch('/comment/' + bno + '/' + page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

function spreadCommentList(bno, page = 1){
    getCommentListFromServer(bno, page).then(result => {
        console.log(result);

        const ul = document.getElementById('cmtListArea');
        
        if(page == 1){
            ul.innerHTML = '';
        }

        if(result.cmtList.length > 0){
            for(let cvo of result.cmtList){
                let li = `<li class="list-group-item" data-cno="${cvo.cno}" data-writer=${cvo.writer}>`;
                li += `<div class="mb-3">`;
                li += `<div class="fw-bold">${cvo.writer}</div>`;
                li += `${cvo.content}`;
                li += `</div>`;
                li += `<span class="badge text-bg-warning">${cvo.modAt}</span>`;   
                li += `<button type="button" class="btn btn-outline-warning btn-sm mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                li += `<button type="button" class="btn btn-outline-danger btn-sm del">삭제</button>`;
                li += `</li>`;
                ul.innerHTML += li;
            }
            //page 처리
            let moreBtn = document.getElementById('moreBtn');
            //현재 페이지 번호가 전체 페이지 번호보다 작다면
            //아직 나와야 할 페이지가 더 있다면...
            console.log(moreBtn);

            if(result.pgvo.pageNo < result.endPage){
                //순김 속성 해지
                moreBtn.style.visibility = 'visible'; //표시
                //페이지 + 1
                moreBtn.dataset.page = page + 1;
            }else{
                moreBtn.style.visibility = 'hidden'; //숨김
            }
        }else{
            let li = `<div class="accordion-body">Comment List Empty</div>`;
            ul.innerHTML = li;
        }
    })
}

document.addEventListener('click', (e)=>{
    console.log(e.target);
    if(e.target.id == 'moreBtn'){
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    }else if(e.target.classList.contains('mod')){
        //타겟에 가장 가까운 li 찾기 : 내 버튼이 포함되어 있는 li 찾기
        let li = e.target.closest('li');
        //nextSibling : 같은 부모의 다음 형제 객체를 반환
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        //nodeValue : 현재 선택한 노드의 value 반환
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;

        document.getElementById('cmtModBtn').setAttribute("data-cno", li.dataset.cno);
        document.getElementById('cmtModBtn').setAttribute("data-writer", li.dataset.writer);
    }else if(e.target.id == 'cmtModBtn'){
        let cmtDataMod = {
            cno: e.target.dataset.cno,
            writer: e.target.dataset.writer,
            content: document.getElementById('cmtTextMod').value
        };
        console.log(cmtDataMod);

        editCommentToServer(cmtDataMod).then(result => {
            if(result == "1"){
                alert("댓글 수정 성공");
                document.querySelector('.btn-close').click();
            }else{
                alert("댓글 수정 실패");
                document.querySelector('.btn-close').click();
            }
            spreadCommentList(bnoVal);
        })
    }else if(e.target.classList.contains('del')){
        let li = e.target.closest('li');
        let cnoVal = li.dataset.cno;

        console.log(cnoVal);

        deleteCommentToServer(cnoVal).then(result => {
            if(result == "1"){
                alert("댓글 삭제 성공");
            }else{
                alert("댓글 삭제 실패");
            }
            spreadCommentList(bnoVal);
        })
    }
})

async function editCommentToServer(cmtDataMod){
    try {
        const url = '/comment/edit';
        const config = {
            method: 'put',
            headers: {
                'content-type':'application/json; charset=utf-8'
            },
            body: JSON.stringify(cmtDataMod)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

async function deleteCommentToServer(cnoVal){
    try {
        const url = '/comment/delete/' + cnoVal;
        const config = {
            method: 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}