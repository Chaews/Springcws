boardlist();

// 2. R 출력 처리 메소드
function boardlist(){
    $.ajax({
        url : "/board/getboardlist",
        method : "GET",
        success : function(result){
            html = '<thead><tr><th>번호</th><th>제목</th><th>작성일</th><th>조회수</th><th>좋아요수</th></tr></thead>';
            for(let i = 0 ; i < result.length ; i++ ){
                html+= '<tr><td>'+result[i].bno+'</td><td><a href="/board/view/'+result[i].bno+'">'+result[i].btitle+'</span></td><td>'+result[i].bindate+'</td><td>'+result[i].bview+'</td><td>'+result[i].blike+'</td></tr>'
            }
            $("#boardtable").html(html);
        }
    })
}


//function view(bno){
//    $.ajax({
//        url : "/board/view/" +bno,
//        success : function(result){
//
//        }
//    })
//}
