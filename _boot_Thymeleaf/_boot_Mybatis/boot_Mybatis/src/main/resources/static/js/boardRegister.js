console.log("boardRegister in~~!!");
document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('files').click();
});

const regExp = new RegExp("\.(exe|sh|bat|js|dll|msi)$"); //실행파일 막기
//const regExpImg = new RegExp("\.(jpg|jpeg|png|bmp|gif)$");
const maxSize = 1024 * 1024 * 2;

function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else{
        return 1;
    }
};

document.addEventListener('change', (e)=>{
    if(e.target.id == 'files'){
        //multiple 배열로 들어옴
        const fileObject = document.getElementById('files').files;
        console.log(fileObject);
        document.getElementById('regBtn').disabled = false;

        const div = document.getElementById('fileZone');
        //이전에 업로드했던 파일들이 있다면 제거
        div.innerHTML = "";
        let ul = `<ul calss="list-group list-group-flush" />`;

        let isOk = 1; //여러 파일에 대한 값. 확인에 대한 값
        for(let file of fileObject){
            let validResult = fileValidation(file.name, file.size);
            isOk *= validResult; //하나씩 모든 파일에 대한 확인
            ul += `<li class="list-group-item">`;
            ul += `<div class="mb-3">`;
            ul += `${validResult ? '<div class="fw-bold text-primary">업로드 가능</div>' : '<div class="fw-bold text-danger">업로드 불가능</div>'}`;
            ul += `${file.name}</div>`;
            ul += `<span class="badge rounded-pill text-bg-${validResult ? 'success' : 'danger'}">${file.size}Byte</span>`;
            ul += `</li>`;
        }

        ul += `</ul>`;
        div.innerHTML = ul;
    }

    if(isOk == 0){
        document.getElementById('regBtn').disabled = true;
    }
})
