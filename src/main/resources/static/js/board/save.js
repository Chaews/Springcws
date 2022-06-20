// 1. C 쓰기 처리 메소드
function boardsave(){
    let form = $("#boardform")[0];
    let formdata = new FormData(form);
    $.ajax({
        url : "/board/save",
        data : formdata,
        method : "POST",
        processData : false,
        contentType : false,
        success : function(result){
            alert("등록 완료");
            location.href="/board/list";
        }
    })
}