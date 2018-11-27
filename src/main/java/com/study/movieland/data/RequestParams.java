package com.study.movieland.data;

import java.util.Objects;

public class RequestParams {

    private String sortFieldName;
    private SortDirection sortDirection;
    private String searchText;
    private int pageNumber;
    private int countOnPage;

    public boolean isSorted() {
        return sortFieldName != null && !sortFieldName.isEmpty();
    }

    public boolean isFiltered() {
        return searchText != null && !searchText.isEmpty();
    }

    public boolean isPaginated() {
        return countOnPage != 0;
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public String getSearchText() {
        return searchText;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getCountOnPage() {
        return countOnPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestParams)) return false;
        RequestParams that = (RequestParams) o;
        return pageNumber == that.pageNumber &&
                countOnPage == that.countOnPage &&
                Objects.equals(sortFieldName, that.sortFieldName) &&
                sortDirection == that.sortDirection &&
                Objects.equals(searchText, that.searchText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortFieldName, sortDirection, searchText, pageNumber, countOnPage);
    }

    public static class Builder {

        private RequestParams requestParam;

        public Builder() {
            requestParam = new RequestParams();
        }

        public Builder sortFieldName(String value) {
            requestParam.sortFieldName = value;
            return this;
        }

        public Builder sortDirection(SortDirection value) {
            requestParam.sortDirection = value;
            return this;
        }

        public Builder sortDirection(String value) {
            return sortDirection(SortDirection.getByString(value));
        }

        public Builder searchText(String value) {
            requestParam.searchText = value;
            return this;
        }

        public Builder pageNumber(int value) {
            requestParam.pageNumber = value;
            return this;
        }

        public Builder countOnPage(int value) {
            requestParam.countOnPage = value;
            return this;
        }

        public RequestParams build() {
            return requestParam;
        }
    }
}