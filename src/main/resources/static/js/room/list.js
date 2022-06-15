let list = [];
// GeoLocation을 이용해서 접속 위치를 얻어옵니다
navigator.geolocation.getCurrentPosition(function(position) {
    var lat = position.coords.latitude, // 위도
        lon = position.coords.longitude; // 경도
    var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
        center : new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
        level : 4 // 지도의 확대 레벨
    });

    $.ajax({
        url : '/room/roomlist',
        data : JSON.stringify(map.getBounds()),
        contentType : 'application/json',
        method : 'POST',
        success : function(data) {
        let html ="";
            if(data.positions.length==0){ html = "<div>검색된 방이 없습니다</div>"; }
            var markers = $(data.positions).map(function(i, position) {
                var marker = new kakao.maps.Marker({
                        position : new kakao.maps.LatLng(position.rlat, position.rlng),
                        image : markerImage
                });
                kakao.maps.event.addListener(marker, 'click', function() {
                    getroom(position.rno);
                });

                // 사이드바에 추가할 html 구성

                html +=
                '<div class="row">'+
                '<div class="col-md-6">'+
                '<img src="/upload/'+position.rimg+'" width="100%">'+
                '</div>'+
                '<div class="col-md-6">'+
                '<div> 집번호 : <span> '+position.rno+' </span> </div>'+
                '<div> 집이름 : <span> '+position.rtitle+' </span> </div>'+
                '</div>'+
                '</div>';

                return marker ;
            });
            clusterer.addMarkers(markers);
            // 해당 html을 해당 id값에 추가

            $("#sidebar").html(html);


        }
    })


    // 마커 클러스터러를 생성합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom 값을 true로 지정하지 않은 경우
    // 클러스터 마커를 클릭했을 때 클러스터 객체가 포함하는 마커들이 모두 잘 보이도록 지도의 레벨과 영역을 변경합니다
    // 이 예제에서는 disableClickZoom 값을 true로 설정하여 기본 클릭 동작을 막고
    // 클러스터 마커를 클릭했을 때 클릭된 클러스터 마커의 위치를 기준으로 지도를 1레벨씩 확대합니다
    var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 6, // 클러스터 할 최소 지도 레벨
        disableClickZoom: true // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
    });

    // 마커 클러스터러에 클릭이벤트를 등록합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
    // 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
    kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {

        // 현재 지도 레벨에서 1레벨 확대한 레벨
        var level = map.getLevel()-1;

        // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
        map.setLevel(level, {anchor: cluster.getCenter()});
    });

    // 지도 시점 변화 완료 이벤트를 등록한다 [ idle(드래그 완료시) vs bounds_changed(드래그 중에 이벤트 발생) ]
    kakao.maps.event.addListener(map, 'idle', function () {
        clusterer.clear();
        let html = "";
        $.ajax({
            url : '/room/roomlist',
            data : JSON.stringify(map.getBounds()),
            contentType : 'application/json',
            method : 'POST',
            success : function(data) {
            list = data.positions;
                if(data.positions.length==0){ html = "<div>검색된 방이 없습니다</div>"; }
                var markers = $(data.positions).map(function(i, position) {
                    var marker = new kakao.maps.Marker({
                            position : new kakao.maps.LatLng(position.rlat, position.rlng),
                            image : markerImage
                    });
                    kakao.maps.event.addListener(marker, 'click', function() {
                        // 해당 모달에 데이터 넣기
                        getroom(position.rno);
                        // 모달 띄우기
                    });

                    // 사이드바에 추가할 html 구성
                    html +=
                    '<div class="row" onclick="getroom('+position.rno+')">'+
                    '<div class="col-md-6">'+
                    '<img src="/upload/'+position.rimg+'" width="100%">'+
                    '</div>'+
                    '<div class="col-md-6">'+
                    '<div> 집번호 : <span> '+position.rno+' </span> </div>'+
                    '<div> 집이름 : <span> '+position.rtitle+' </span> </div>'+
                    '</div>'+
                    '</div>';

                    return marker ;
                });
                clusterer.addMarkers(markers);
                // 해당 html을 해당 id값에 추가

                $("#sidebar").html(html);



            }
        })
    });
    var markerImageUrl = 'http://localhost:8082/img/markerimg.png',
        markerImageSize = new kakao.maps.Size(40, 42), // 마커 이미지의 크기
        markerImageOptions = {
            offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
        };
    var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);


});


function getroom(rno){
    $.ajax({
        url : "/room/getroom",
        data : {"rno" : rno},
        success : function(room){
            let imgtag = "";
            let detailhtml = "" ;
            // 응답받은 데이터를 모달에 넣기
            for(let i = 0 ; i < room["rimglist"].length ; i++) {
            console.log( room.rimglist[i]);
                if(i==0){
                    imgtag += '<div class="carousel-item active"><img src="/upload/'+room.rimglist[i]+'" class="d-block w-100" alt="..."></div>';
                }
                else{
                    imgtag += '<div class="carousel-item"><img src="/upload/'+room.rimglist[i]+'" class="d-block w-100" alt="..."></div>';
                }
            }

            detailhtml += '방번호 : ' + room["rno"] + '<br>' +
                          '방이름 :  ' + room["rtitle"] + '<br>' +
                          '거래방식 :  ' + room["rtrans"] + '<br>' +
                          '가격 :  ' + room["rprice"] + '<br>' +
                          '면적 :  ' + room["rspace"] + '<br>' +
                          '관리비 :  ' + room["rmanagementfee"] + '<br>' +
                          '층 :  ' + room["rfloor"] + '<br>' +
                          '전체층수 :  ' + room["rmaxfloor"] + '<br>' +
                          '구조 :  ' + room["rstructure"] + '<br>' +
                          '준공날짜 :  ' + room["rcompletiondate"] + '<br>' +
                          '입주가능일 :  ' + room["rindate"] + '<br>' +
                         // '주차 여부 :  ' + room["rparking"] + '<br>' +
                         // '엘리베이터 여부 :  ' + room["relevator"] + '<br>' +
                          '건물 종류 :  ' + room["rkind"] + '<br>' +
                          '주소  :  ' + room["raddress"] + '<br>' +
                          '상세설명 :  ' + room["rcontents"] + '<br>' +
                          '상태 :  ' + room["ractive"]+ '<br>';

            $("#detail").html(detailhtml);
            $("#csimg").html(imgtag);
            $("#modalbtn").click();
        }
    })
}