$.ajax({
    url : "/room/myroomlist",
    method : "GET",
    success : function(result){
        let html ="";
        for(let i=0; i<result.length; i++){
            html +=
            '<tr>'+
                '<td width="10%"> <img src="/upload/'+result[i].rimg+'" width="100%"></td>'+
                '<td><span> '+result[i].rtitle+' </span></td>'+
                '<td><span> '+result[i].rdate+' </span></td>'+
                '<td><button onclick="myroomupdate('+result[i].rno+')">수정</button> <button onclick="myroomdelete('+result[i].rno+')">삭제</button></td>'+
            '</tr>';

        }
        $("#myroomtable").html(html);
    }
})

function myroomdelete(rno){
    $.ajax({
        url : "/room/myroomdelete",
        data : {"rno":rno},
        method : "DELETE",
        success : function(result){
            if(result){
                alert("삭제 성공");
                location.href="/"
            }
            else{
                alert("삭제 실패");
            }
        }
    })
}