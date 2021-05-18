import { rscApi } from '@/services/api';

const state = {
  dog: {
    // 멍플레이스 
    selectedItem: null ,
  },
  like: {
    // 좋아요 포스팅 
    nowLat: 0,
    nowLon: 0,
    tempPhotoUrl: '',
    likeCnt: 0, 
    // 지역설정 관련
    startArea: '', 
    areaName: '', 
    // 포스팅 후 
    wid: null, 
  },
  // 이동 경로 
  path: [],
  // 핫플레이스 전체리스트, top5 담는 곳
  hotPlace: [],
  postingWid: [],
  myPostingContent: [],
  // 시작 시간 관련 
  startTime: '',
  beforeH: '', 
  beforeM: '', 
};
const getters = {
  //처음 시작했을때 wid
  getWid(state){
    return state.like.wid
  },
  // 멍플레이스 
  getSelectedItem(state) {
    return state.dog.selectedItem
  },
  // 좋아요 포스팅 
  getNowLat(state){
    return state.like.nowLat
  },
  getNowLon(state){
    return state.like.nowLon
  },
  getTempPhotoURL(state){
    return state.like.tempPhotoUrl
  },
  getFirstAreaName(state){
    return state.like.startArea
  },
  getAreaName(state){
    return state.like.areaName
  },
  getLikeCnt(state){
    return state.like.likeCnt
  },
  // 경로 
  getMyPath(state){
    return state.path
  },
  // 내가 쓴 게시글 관련 
  getPostingWid(state){
    return state.postingWid
  },
  getMyPostingContent(state){
    return state.myPostingContent
  },
  getHotPlace(state) {
    return state.hotPlace
  },
  // 시작시간 가져오기
  getStartTime(state){
    return state.startTime
  },
  // 산책 시간 계산 
  getbeforeH(state){
    return state.beforeH
  },
  getbeforeM(state){
    return state.beforeM
  },
  
};
const mutations = {
  // 글 작성 후 wid 
  setWid(state, data){
    console.log(data, 'setWid안에서')
    state.like.wid = data 
  },
  // 멍플레이스 
  setSelectedItem(state, data){
    state.dog.selectedItem = data
  },
  // 좋아요 포스팅 
  setNowLat(state, data){
    state.like.nowLat = data
  },
  setNowLon(state, data){
    state.like.nowLon = data 
  },
  // 임시 사진 url 
  setTempPhotoURL(state, data){
    state.like.tempPhotoUrl = data 
  },
  // 산책시작 지역정보 
  setFirstAreaName(state, data){
    state.like.startArea = data
  },
  // 지역정보
  setAreaName(state, data){
    state.like.areaName = data
  },
  // 내가 간 경로 
  setMyPath(state, data){
    state.path.push(data)
  },
  // 경로 삭제
  deleteMyPath(state){
    state.path = []
  },
  // posting한 게시글 id 
  setPostingWid(state, data){
    state.postingWid.push(data)
  },
  //멍플레이스 전체글 
  setHotPlace(state, data) {
    state.hotPlace = data
  },
  // 내가 쓴 게시글들 wid 지우기
  deletePostingWid(state){
    state.postingWid = []
  },
  //내가 쓴 게시글 요청 return값 
  setMyPostingContent(state, data){
    state.myPostingContent = data
  },
  // 내가 쓴 게시글 내용 지우기 
  deletePostingContent(state){
    state.myPostingContent = []
  },
  setStartTime(state, data){
    state.startTime = data 
  },
  setbeforeH(state, data){
    state.beforeH = data 
  },
  setbeforeM(state, data){
    state.beforeM = data 
  },
  // 좋아요 게시글 올린 갯수 갱신
  setLikeCnt(state, data){
    state.like.likeCnt = data
  }
};
const actions = {
  // 산책 시작
  sendStartWalkInApi(context, paramas){
    return rscApi.post('walk/start', paramas)
      .then((res)=>{
        context.commit('setWid', res.data.wid)
      }).catch((error)=>{
        console.log(error)
      })
  },
  // 업로드 사진 미리보기 
  makeTempPhotoUrlInApi(context, params){
    return rscApi.post('place/imageUpload', params)
      .then((res)=> {
        // tempPhotoURL 갱신 
        context.commit('setTempPhotoURL', res.data.url)
      })
  },
  // 좋아요 사진, 위도, 경도, 의견 백엔드 전송
  sendNowPostInApi(context, params){
    return rscApi.post('place/likePlace', params)
      .then((res) =>{
        console.log(res)
        context.commit('setPostingWid', res.data.lid)
      }).catch((error) =>{
        return error
      })
  },
  // 산책 종료 
  doneWalkInApi(context, params){
    console.log(params, '산책종료함수에 넘겨주는 params')
    return rscApi.post('walk/end', params)
      .then((res) => {
        console.log(res)
      }).catch((error) =>{
        console.log(error)
      })
  },
  // 내가 쓴 게시글 리스트 받아오기 
  getMyPlaceListInApi(context, params){
    return rscApi.post('walk/likeList',params)
      .then((res) => {
        console.log(res.data.likeList, 'res.data.likeList')
        context.commit('setMyPostingContent', res.data.likeList)
        console.log(state.myPostingContent, 'my postingcontent에 어떤 형식으로 들어가니')
        return res
      }).catch((error) =>{
        console.log(error, '내가 쓴 게시글 가져오기 실패')
      })
  },
  // 핫플레이스 전체 리스트 받아오기
  getHotPlaceListInApi(context, place) {
    console.log(place)
    var pop = 'pop'
    return rscApi.get(`place/list/${place}?sort=${pop}`)
    .then((res) => {
      context.commit('setHotPlace', res.data.postList)
    })
  },
  // 핫플레이스 TOP 5 받아오기
  getTop5ListInApi(context, place) {
    return rscApi.get(`place/top5/${place}`)
    .then((res) => {
      console.log(res.data.postList, 'top5')
      context.commit('setHotPlace', res.data.postList)
    })
  },
  // 핫플 포스트 디테일 
  setClickPostDetailInAPI(context, lid){
    return rscApi.get(`place/detail/${lid}`)
      .then((res)=> {
        context.commit('setSelectedItem', res.data.postDetail)
      }).catch((error)=>{
        console.log(error)
      })
  },
  // 내 게시글 삭제
  deleteMyPostingInApi(context, params){
    console.log(params)
    return rscApi.delete(`place/delete/${params.peid}?lid=${params.lid}`)
      .then((res)=>{
        console.log('내 게시글 삭제 성공')
        return res
      }).catch((error)=>{
        console.log(error)
      })
  }, 
};

export default {
  state: {
    ...state,
  },
  getters,
  mutations,
  actions,
};
