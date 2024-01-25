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

async function getCommentListFromServer(bno){
    try {
        const resp = await fetch('/comment/' + bno);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

function spreadCommentList(bno){
    getCommentListFromServer(bno).then(result => {
        console.log(result);

        const ul = document.getElementById('cmtListArea');
    
        if(result.length > 0){
            ul.innerHTML = '';
            for(let i = 0; i < result.lengthl; i++){
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
        }else{
            let li = `<div class="accordion-body">Comment List Empty</div>`;
            ul.innerHTML = li;
        }
    })
}