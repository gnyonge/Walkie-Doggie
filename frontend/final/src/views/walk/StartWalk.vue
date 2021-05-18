<template>
  <div>
    <div id="mainBox" style="margin-top: 10%;">
      <div id="app">
        <!-- 지도 -->
        <div id="map"></div>
      </div>
      <div class="btncalss">
        <!-- 좋아요 -->
        <v-btn
          large
          id="mainBtn"
          style="width: 10px;"
          @click="like">
          <v-icon style="color: #FC6C8C;">mdi-cards-heart</v-icon> 
        </v-btn>
        <!-- 산책 종료  -->  
        <v-dialog
            transition="dialog-bottom-transition"
            max-width="600">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            large
            v-bind="attrs"
            v-on="on"
            id="mainBtn"
            style="width: 10px; "
            @click="doneWalk">
            <v-icon style="color: #F2B75B">mdi-file-excel-box-outline</v-icon>
          </v-btn>
        </template>
        <template>
          <v-card>
            <v-card-text>
              <div style="text-align: center;">
                <p><v-icon style="font-size: 120px;color: #7CE793;">mdi-check</v-icon></p>
                <p>산책 시작 시간 :<br> {{ start }} </p>
                <p>산책 종료 시간 :<br> {{ end }} </p>
                <p>총 산책 시간 : {{ totalH }}시간 {{ totalM}}분</p>
                <p>좋아요 수 : {{ likecnt }}</p>
              </div>
            </v-card-text>
              <v-card-actions class="justify-center">
                <v-btn
                  text
                  id="mainBtn"
                  style="background-color: #FC6C8C;"
                  @click="gotoMain">
                  창 닫기
                </v-btn>
              </v-card-actions>
            </v-card>
          </template>
        </v-dialog>
      </div>
    </div>
    <ImageItem v-if="this.getSelectedItem != null" />
    <div class="d-flex justify-center img">
      <v-img
        height="80"
        class="rounded-circle gird"
        max-height="80"
        max-width="80"
        :src= getDogInfo.pet.pe_profile_photo>
      </v-img>
      
    </div>
  </div>
</template>


<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import ImageItem from '@/components/walk/ImageItem'

export default {
  name: 'StartWalk',
  components: {
    ImageItem
  },
  data() {
    return {
      // 지도 
      mapContainer: '',
      mapOption: '',
      map: {},
      // 시간 
      start: '', 
      end: '', 
      // 총 시간
      beforeH: 0,
      beforeM:0,
      afterH:0,
      afterM: 0,
      totalH: 0,
      totalM: 0, 
      // 선호도 
      likecnt: 0,
      // 실시간 위치 
      walkLoc : '',
      linePath: [], 
      myLikePath: [],
      // 멍플레이스
      likePath: [],
      
    }
  },
  computed: {
    ...mapGetters([
      'getNowTab', 
      'getMyPath', 
      'getFirstAreaName', 
      'getAreaName', 
      'getMyPostingContent',
      'getStartTime',
      'getbeforeH',
      'getbeforeM',
      'getPostingWid',
      'getDogInfo',
      'getMyPostingContent',
      'getLikeCnt',
      'getWid',
      'getSelectedItem'
    ])
  },
  mounted() {
    console.log(this.getPostingWid, '내가쓴 게시글 ')
    // 좋아요 작성시 해당 리스트 가져오기 
    if(this.getPostingWid.length !== 0){
      this.getMyPlaceListInApi(this.getPostingWid)
      .then((res) => {
        this.setMyPostingContent(res.data.likeList)
        // 내가 쓴 게시글들을 핀으로 보여주기
        this.myLikePoint()
      })
    }
    console.log(this.getFirstAreaName)
    // 첫화면과 구별 
    if (window.kakao && window.kakao.maps) {
      this.initMap();
      if( this.getFirstAreaName === '') {
        // 실시간 위치 정보 
        this.navigation()
      } else { // 좋아요 포스팅 이후 
        // 다시 들어올 떄마다 경로 받기 
        this.linePath = this.getMyPath
        this.getLocation() 
      }
    } else {
      const script = document.createElement('script');
      // global kakao
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = 'https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6985aa694c9e9631fa03de6f22217f30';
      document.head.appendChild(script);
    }

  },
  beforeDestroy() {
    // 실시간 정보 멈춤
    clearInterval(this.walkLoc)
    this.deletePostingWid
  },
  methods: {
    ...mapMutations([
      'setNowTab', 
      'setNowLon', 
      'setNowLat',
      'setMyPath',
      'setFirstAreaName',
      'setAreaName', 
      'deleteMyPath',
      'setTempPhotoURL',
      'deletePostingWid',
      'setStartTime',
      'setbeforeH',
      'setbeforeM',
      'deletePostingContent',
      'setMyPostingContent',
      'setLikeCnt',
    ]), 
    ...mapActions([
      'doneWalkInApi', 
      'getMyPlaceListInApi',
      'sendStartWalkInApi',
      'setClickPostDetailInAPI']),
    // 지도 첫 화면 로드 
    initMap() {
      this.mapContainer = document.getElementById('map');
      this.mapOption = {
        //한강 주소 
        center: new kakao.maps.LatLng(37.507781796841705, 126.99274641516297),
        draggable: true,
        level: 5
      };
    
      this.linePath = this.getMyPath
      this.map = new kakao.maps.Map(this.mapContainer, this.mapOption); // 지도를 생성합니다
    
 
      // HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
      if (navigator.geolocation) {
        
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition((position)=> { 
          var lat = position.coords.latitude, // 위도
              lon = position.coords.longitude; // 경도
   
          this.linePath.push(new kakao.maps.LatLng(lat, lon))
          // 첫위치 마커 표시
          if(this.getFirstAreaName === ''){
            // 첫위치 위도 -> 주소 
            this.getAddress(lon, lat)
            var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                message = '<div style="padding:5px;">산책 시작.</div>'; // 인포윈도우에 표시될 내용입니다
            
            // 마커와 인포윈도우를 표시합니다
            displayMarker(locPosition, message);
          }else{ // 좋아요 표시 후 마커 
            var middlelocPosition = new kakao.maps.LatLng(lat, lon)
            middleDisplayMarker(middlelocPosition) 
          } 
        });
      } else { // HTML5의 GeoLocation을 사용할 수 없을때 
          var errorlocPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
          errormessage = 'geolocation을 사용할수 없어요..'
        
          displayMarker(errorlocPosition, errormessage);
      }
    
      var map = this.map

      // 지도에 마커와 인포윈도우를 표시하는 함수입니다
      function displayMarker(locPosition, message) {
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({  
            map: map, 
            position: locPosition
        }); 

        var iwContent = message, // 인포윈도우에 표시할 내용
            iwRemoveable = true;

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
            content : iwContent,
            removable : iwRemoveable
        });
          
        // 인포윈도우를 마커위에 표시합니다 
        infowindow.open(map, marker);

        // message창 3초 후에 종료 
        setTimeout(function(){ 
          infowindow.close()
          marker.setMap(null);}, 3000
        )
        
        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);  
      }  
      // 중간에 찍어주는 위치 
      function middleDisplayMarker(markerPosition){
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
        position: markerPosition
        });
        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

        setTimeout(function(){ 
          marker.setMap(null);}, 3000
        )
        
        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(markerPosition);  
      }
    },
    // 위치 -> 주소 
    getAddress(lon, lat){
      var fullAddress = ''
      const callback =  (result, status) => {
        if (status === kakao.maps.services.Status.OK) {
          var address = result[0].address.address_name
          var detail = address.split(' ')
          if (this.getFirstAreaName === '') {
            fullAddress = this.makeFullAd(detail)
            console.log(fullAddress, '첫주소 설정')
            this.setFirstAreaName(fullAddress)
            // 시작 시간 가져오기 
            this.setStartTime(this.startTime())
            // 시작 api 호출 
            this.sendStartWalkInApi({
              peid:  this.getDogInfo.pet.peid,
              w_location: this.getFirstAreaName
            }).then(()=> {
              console.log('시작 api전송 성공')
            })

          }else {
            fullAddress = this.makeFullAd(detail)
            console.log(fullAddress, '좋아요 후 주소 설정')
            this.setAreaName(fullAddress)
          }
        } 
      }
      //주소-좌표 변환 객체 생성 
      var geocoder = new kakao.maps.services.Geocoder();
      function searchDetailAddrFromCoords(lon, lat, callback) {
        // 좌표로 법정동 상세 주소 정보를 요청합니다
        geocoder.coord2Address(lon, lat, callback);
      }
      searchDetailAddrFromCoords(lon, lat, callback)
    },
    // 동까지 저장하는 함수 
    makeFullAd(detail){
      let sAddress = []
      // 주소의 동까지 저장 
      for (let i in detail) {
        // 인덱스
        // console.log(i)  
        let checkAddress = detail[i]
        if(checkAddress.charAt(checkAddress.length-1) !== '동'){ 
          sAddress.push(checkAddress)
        }else {
          sAddress.push(checkAddress)
          break
        }
      }
      // console.log(sAddress, '동까지 저장')
      const finalAddress = sAddress.join(" ");
      return finalAddress
    },
    // 시간 가져오기 
    getTime() {
      let today = new Date() 
      console.log(today)
      let date = today.getFullYear() + '년' + (today.getMonth() + 1 ) + '월' + today.getDate() + '일'
      let time = today.getHours() + '시' + today.getMinutes() + '분'
      this.afterH = today.getHours()
      this.afterM = today.getMinutes()
      let dateTime = date + ' ' + time 
      return dateTime
    }, 
    // 시작 시간 가져오기 
    startTime() {
      let today = new Date() 
      let date = today.getFullYear() + '년' + (today.getMonth() + 1 ) + '월' + today.getDate() + '일'
      let time = today.getHours() + '시' + today.getMinutes() + '분'
      this.beforeH = today.getHours()
      this.setbeforeH(this.beforeH)
      this.beforeM = today.getMinutes()
      this.setbeforeM(this.beforeM)
      let dateTime = date + ' ' + time
      return dateTime
    }, 
    // 산책시간 계산 
    calTime(){
      if (this.afterH > this.getbeforeH){
        this.afterH -= 1 
        this.afterM += 60
      }
      this.totalH = this.afterH - this.getbeforeH
      this.totalM = this.afterM - this.getbeforeM
    },
    // 좋아요
    like() {
      this.likecnt = this.getLikeCnt + 1 
      this.setLikeCnt(this.likecnt)

      var t = this

      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function(position) { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        // 여기서 주소 변환해서 넘겨 줘야 할듯
        t.getAddress(lon, lat)
        
        t.setNowLat(lat) 
        t.setNowLon(lon)
        // 좋아요 포스팅  
        t.$router.push('/likeposting')
      })
    },
    // 핀꽂기
    pin(positionslist){
      var t = this
      var map = this.map
      var positions = positionslist
      for (var i = 0 ; i < positions.length; i++){
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(31, 35); 
        var imageOption = {offset: new kakao.maps.Point(16, 34)};
        // 마커 이미지 생성 
        var markerImage = new kakao.maps.MarkerImage(positions[i].imageSrc, imageSize, imageOption);
        //위치 정보 갱신 
        var latlng = new kakao.maps.LatLng(positions[i].lat, positions[i].lon)
        
        // 마커생성 
        var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: latlng, // 마커를 표시할 위치
        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지 
        });
        
        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', getMakerInfo(map, marker))  
      }
      //  마커 클릭시 해당 정보 가져오는 함수 
      function getMakerInfo(map, marker){
        return function(){
        // 마커 선택후 해당 정보 자식 컴포넌트로 전송 
        t.setClickPostDetailInAPI({
          uid:t.getDogInfo.pet.uid, 
          lid: marker.getTitle(),
        }).then(()=>{
          console.log('선택한거 잘 가냐')
        })
        console.log(marker.getTitle())
       
        }
      }
      if (marker !== undefined){
        marker.setMap(map)
      }
      
      console.log('pin 함수 안에 setMap 있냐')
    },
    // 형식 변환 
    formatConversion(posts){
      var newPosition = []
      for ( var i of posts){
        // 조건에 맞는 핀 이미지 연결 
        let img = ''
        if(i.l_desc === '사진이 잘 나와요!'){
          img = 'https://i.ibb.co/XWGzFdp/nicephoto.png'
        }else if(i.l_desc === '날씨가 좋아요!'){
          img = 'https://i.ibb.co/LhFSjwN/niceweather.png'
        }else if(i.l_desc === '마킹을 했어요!') {
          img = ' https://i.ibb.co/W2s6GbW/marking.png'
        }else{ // 휴식 공간이 있어요!
          img = 'https://i.ibb.co/pybCvBz/resting.png'
        } 
        newPosition.push({
          title: i.lid,
          lat: i.p_latitude,
          lon: i.p_longtitude,
          imageSrc: img
        })
      }
      return newPosition
    },
    // 나의 좋아요에 핀 꽂기 
    myLikePoint(){
      var newPosition = this.formatConversion(this.getMyPostingContent)
      this.pin(newPosition)
    },
    // 산책종료
    doneWalk() {
      this.start = this.getStartTime
      this.end = this.getTime()
      this.likecnt = this.getLikeCnt
      
      // 백엔드로 정보 보내기 
      this.doneWalkInApi({
        wid: this.getWid,
        w_like: this.getLikeCnt,
        w_time: (this.totalH * 60) + this.totalM,
      }).then(()=> {
        // 실시간 정보 가져오기죽이기 
        clearInterval(this.walkLoc)
        this.calTime()
        // 저장되어 있던 정보도 지우기 
        this.deletePostingContent()
        this.deletePostingWid()
        this.deleteMyPath()
        this.setNowLon(0)
        this.setNowLat(0)
        this.setFirstAreaName('')
        this.setTempPhotoURL('')
        this.setAreaName('')
        this.setLikeCnt(0)
      }).catch((error)=> {
        console.log(error)
      })
      
    },

    
    // 메인으로 보내기 
    gotoMain(){
      // 하단바 색상 변경 
      this.setNowTab(1)
      this.$router.push('/walk')
    },

    // 움직이는 경로 표시하기 
    navigation(){
      this.walkLoc = setInterval(this.getLocation, 10000)
    },
    
    // 위치 정보 기반 선 표시 
    getLocation() {
      var map = this.map
      var linePath = this.linePath
      var t = this 

      // GeoLocation을 이용해서 접속 위치를 얻어옵니다
      navigator.geolocation.getCurrentPosition(function(position) { 
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
      linePath.push(new kakao.maps.LatLng(lat, lon))

      // 실시간 위치 정보 vuex로 보내기 
      t.setMyPath(new kakao.maps.LatLng(lat, lon))
      
      // 선 연결 
      var polyline = new kakao.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표배열 
        strokeWeight: 5, // 선의 두께
        strokeColor: '#1E90FF', // 선의 색깔 
        strokeOpacity: 0.2, // 선의 불투명도, 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid' // 선의 스타일입니다
      })
      polyline.setMap(map)
      
      })
    }
  },
}
</script>

<style>
#map {
    width: auto;
    height: 480px;
}
.img{
  position: absolute;
  margin: 10% 40%;
  z-index: 1;
}
.gird{
  border: 5px solid rgb(250, 250, 250);

}
.btncalss{
  position: absolute;
  text-align: center;
  display: inline-block;
  margin: 2% 0%; 
  width: 295px;
  display: flex;
  justify-content: space-between;
  z-index: 1;
}

</style>