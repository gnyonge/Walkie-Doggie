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
};

export default {
  state: {
    ...state,
  },
  getters,
  mutations,
  actions,
};
