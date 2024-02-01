console.log("boardDetail js in~~!!");

document.getElementById('listBtn').addEventListener('click', ()=>{
    location.href = "/board/list";
})

document.getElementById('modBtn').addEventListener('click', ()=>{
    document.getElementById('title').readOnly = false;
    document.getElementById('content').readOnly = false;  

    let modBtn = document.createElement('button');
    modBtn.setAttribute('type', 'submit');
    modBtn.classList.add('btn', 'btn-outline-warning');
    modBtn.innerText = "Submit";
    document.getElementById('modForm').appendChild(modBtn);
    
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();
})

document.getElementById('delBtn').addEventListener('click', ()=>{
    document.getElementById('delForm').submit();
    
})