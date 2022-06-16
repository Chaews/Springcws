function member_delete(){
    $.ajax({
        url : "/member/delete",
        method : "DELETE",
        data : {"mpassword" : $("#mpassword").val()},
        success : function(result){
            if(result){
                alert("성공");
                location.href ="/member/logout";
            }
            else{
                alert("실패");
            }
        }
    })
}