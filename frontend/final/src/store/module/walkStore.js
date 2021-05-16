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
    // 지역설정 관련
    startArea: '', 
    areaName: '', 
    // 포스팅 후 
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
  getMyPath(state){
    return state.path
  },
  getPostingWid(state){
    return state.postingWid
  },
  getHotPlace(state) {
    return state.hotPlace
  },
  getMyPostingContent(state){
    return state.myPostingContent
  },
  getStartTime(state){
    return state.startTime
  },
  getbeforeH(state){
    return state.beforeH
  },
  getbeforeM(state){
    return state.beforeM
  },
};
const mutations = {
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
  setStartTime(state, data){
    state.startTime = data 
  },
  setbeforeH(state, data){
    state.beforeH = data 
  },
  setbeforeM(state, data){
    state.beforeM = data 
  },
};
const actions = {
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
        context.commit('setPostingWid', res.data.lid)
      }).catch((error) =>{
        console.log(error, '포스팅 실패')
        
      })
  },
  // 산책 종료 
  doneWalkInApi(context, params){
    console.log(params)
    return rscApi.post('walk/insert', params)
      .then((res) => {
        console.log(res)
      }).catch((error) =>{
        console.log(error)
      })
  },
  // 내가 쓴 게시글 리스트 받아오기 
  getMyPlaceListInApi(context){
    return rscApi.get('walk/likeList',state.postingWid)
      .then((res) => {
        console.log(res.data.likeList, 'res.data.likeList')
        context.commit('setMyPostingContent', res.data.likeList)
      }).catch((error) =>{
        console.log(error, '내가 쓴 게시글 가져오기 실패')
      })
  },
  // 핫플레이스 전체 리스트 받아오기
  getHotPlaceListInApi(context, place) {
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
  // 내 게시글 수정 
  editMyPostingInApi(){

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
