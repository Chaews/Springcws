boardlist(  1 , 1  , "" , ""  );       //  cno , page , key , keyword
getcategorylist();

let current_cno = 1; // 카테고리 선택
let current_page = 1;
let current_key = ""; // 현재 검색된 키 [ 없을경우 공백 ]
let current_keyword = ""; // 현재 검색된 키워드 [ 없을경우 공백 ]

// 2. R 출력 처리 메소드 [ cno = 카테고리 번호 , key = 검색 키, keyword = 검색내용 ]
function boardlist(cno , page , key , keyword ){

    this.current_cno = cno ;
    this.current_page = page;
//    alert( "현재 카테고리번호 : " + this.current_cno  );
//    alert( "현재 페이지번호 : " + this.current_page  );
    if( key != undefined ) { this.current_key = key; }
    if( keyword != undefined ){ this.current_keyword = keyword; }
    $.ajax({
        url : "/board/getboardlist",
        data : {"cno" :  this.current_cno , "key" :  this.current_key  , "keyword" : this.current_keyword , "page" :  this.current_page } ,
        method : "GET",
        success : function(result){
            let html = '<thead><tr><th width="10%">번호</th><th width="40%">제목</th><th width="20%">작성일</th><th width="10%">조회수</th><th width="10%">좋아요수</th><th width="10%">작성자</th></tr></thead>';
            if(result["data"].length == 0){
                html += '<tr><td colspan="6">검색 결과가 없습니다.</td></tr>'
            }
            else{
                for(let i = 0 ; i < result["data"].length ; i++ ){
                    // 세션에 저장안하고 처리 html+= '<tr><td>'+result[i].bno+'</td><td><a href="/board/view?bno='+result[i].bno+'">'+result[i].btitle+'</span></td><td>'+result[i].bindate+'</td><td>'+result[i].bview+'</td><td>'+result[i].blike+'</td></tr>'
                    html+= '<tr><td>'+result["data"][i].bno+'</td><td><a href="/board/view/'+result["data"][i].bno+'">'+result["data"][i].btitle+'</span></td><td>'+result["data"][i].bindate+'</td><td>'+result["data"][i].bview+'</td><td>'+result["data"][i].blike+'</td><td>'+result["data"][i].mid+'</td></tr>'
                }
            }
            let pagehtml='';
            if(page==1){
                pagehtml+='<li class="page-item"><button class="page-link" type="button" onclick="boardlist('+cno+','+(page)+')">이전</button></li>'
            }
            else{
                pagehtml+='<li class="page-item"><button class="page-link" type="button" onclick="boardlist('+cno+','+(page-1)+')">이전</button></li>'
            }
            for(let i = result["startbtn"] ; i <= result["endbtn"] ; i++){
                pagehtml+='<li class="page-item"><button class="page-link" type="button" onclick="boardlist('+cno+','+(i)+')">'+(i)+'</button></li>'
            }
            if(page==result["totalpage"]){
                pagehtml+='<li class="page-item"><button class="page-link" type="button" onclick="boardlist('+cno+','+(page)+')">다음</button></li>'
            }
            else{
                pagehtml+='<li class="page-item"><button class="page-link" type="button" onclick="boardlist('+cno+','+(page+1)+')">다음</button></li>'
            }

            $("#boardtable").html(html);
            // 페이징버튼 html 넣기
            $("#pagebtnbox").html(pagehtml);

        }
    })
}

function getcategorylist(){
    $.ajax({
        url : "/board/getcategorylist",
        method : "GET",
        success : function(result){
            html = '';
            for(let i = 0 ; i < result.length ; i++ ){
                html+= '<button onclick="categorybtn('+result[i].cno+')" type="button">'+result[i].cname+'</button> ';
            }

//            html += '<button onclick="boardlist('+0+','+""+','+""+')" type="button">전체보기</button>';
            $("#categorybox").html(html);
        }
    })
}

function categorybtn(  cno  ){
    this.current_cno = cno; // 현재 카테고리번호 변경
    boardlist(cno , 1 ,  "" , "" ); // 검색이 없을경우 공백 전달
}

// 검색버튼을 눌렀을때
function search(){
    let key = $("#key").val();
    let keyword = $("#keyword").val();
    // 키 와 키워드 입력받음
    boardlist(  this.current_cno , 1 ,  key , keyword );
}
