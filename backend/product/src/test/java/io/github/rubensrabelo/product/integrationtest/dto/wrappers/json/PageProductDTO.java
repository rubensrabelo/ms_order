package io.github.rubensrabelo.product.integrationtest.dto.wrappers.json;

import io.github.rubensrabelo.product.integrationtest.dto.ProductResponseDTO;

import java.io.Serializable;
import java.util.List;

public class PageProductDTO implements Serializable {

    private List<ProductResponseDTO> content;
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
    private boolean first;
    private boolean last;

    public PageProductDTO() {
    }

    public PageProductDTO(List<ProductResponseDTO> content, int number, int size, int totalPages, long totalElements, int numberOfElements, boolean first, boolean last) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.last = last;
    }

    public List<ProductResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<ProductResponseDTO> content) {
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}