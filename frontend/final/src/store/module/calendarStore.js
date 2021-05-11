import { rscApi } from '@/services/api';

const state = {
  nowTab: 0,
  selectedDate: ""
};
const getters = {
  getNowTab(state) {
    return state.nowTab
  },
  getSelectedDate(state) {
    return state.selectedDate
  }
};
const mutations = {
  setNowTab(state, now) {
    state.nowTab = now
  },
  setSelectedDate(state, date) {
    state.selectedDate = date
  }
};
const actions = {
  createNoPhotoDiaryInApi(context, params) {
    return rscApi.post('diary/insert/np', params)
      .then((res) => {
        console.log(res, '머시냐')
        return res;
      })
      .catch(() => {});
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
