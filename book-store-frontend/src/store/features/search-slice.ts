import { PayloadAction, createSlice } from "@reduxjs/toolkit";

const searchSlice = createSlice({
  name: "search",
  initialState: {
    open: false,
    searchTerm: "",
  },
  reducers: {
    toggleSearch: (state) => {
      state.open = !state.open;
    },
    updateSearchTerm: (state, action: PayloadAction<string>) => {
      state.searchTerm = action.payload;
    },
  },
});

export const { toggleSearch, updateSearchTerm } = searchSlice.actions;

const searchReducer = searchSlice.reducer;

export default searchReducer;
