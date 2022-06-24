$("#bcontent").keyup(function(e){
    if(e.keyCode == 13){
        boardsave();
    }
});

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
            if(result){
                alert("등록 완료");
                location.href="/board/list";
            }
            else{
                alert("로그인 후 작성 가능합니다. [작성 실패]");
            }
        }
    })
}