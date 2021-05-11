const state = {
  selectedItem: null ,
  
};
const getters = {
  getSelectedItem(state) {
    return state.selectedItem
  }
};
const mutations = {
  setSelectedItem(state, data){
    state.selectedItem = data
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
