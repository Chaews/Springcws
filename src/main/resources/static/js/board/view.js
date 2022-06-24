getboard();

// 2. 개별 조회 메소드
function getboard(){
    $.ajax({
        url : "/board/getboard",
        success : function(result){
            let html = '<div>게시물번호 : '+result.bno+'</div>'+
                       '<div>게시물제목 : '+result.btitle+'</div>'+
                       '<div>게시물내용 : '+result.bcontent+'</div>'+
                       '<div>게시물작성일 : '+result.bindate+'</div>'+
                       '<div>게시물수정일 : '+result.bmodate+'</div>'+
                       '<div>게시물조회수 : '+result.bview+'</div>'+
                       '<div>게시물좋아요수 : '+result.blike+'</div>'+
                       '<div>작성자 : '+result.mid+'</div>'+
                       '<button type="button" onclick="boarddelete('+result.bno+')"> 삭제 </button>';
            $("#boarddiv").html(html);
        }
    })
}

// 세션에 저장안하고 처리하기
//function getboard(){
//    $.ajax({
//        method : "POST",
//        success : function(result){
//            let html = '<div>게시물번호 : '+result.bno+'</div>'+
//                       '<div>게시물제목 : '+result.btitle+'</div>'+
//                       '<div>게시물내용 : '+result.bcontent+'</div>'+
//                       '<div>게시물작성일 : '+result.bindate+'</div>'+
//                       '<div>게시물수정일 : '+result.bmodate+'</div>'+
//                       '<div>게시물조회수 : '+result.bview+'</div>'+
//                       '<div>게시물좋아요수 : '+result.blike+'</div>'+
//                       '<button type="button" onclick="boarddelete('+result.bno+')"> 삭제 </button>';
//            $("#boarddiv").html(html);
//        }
//    })
//}

// 4. D 삭제 처리 메소드
function boarddelete(bno){
    $.ajax({
        url : "/board/delete",
        data : {"bno" : bno},
        method : "DELETE",
        success : function(result){
            alert("삭제 완료");
            location.href = "/board/list";
        }
    })
}
