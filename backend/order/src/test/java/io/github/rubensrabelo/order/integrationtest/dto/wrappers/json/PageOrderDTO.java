package io.github.rubensrabelo.order.integrationtest.dto.wrappers.json;

import io.github.rubensrabelo.order.integrationtest.dto.OrderResponseDTO;

import java.io.Serializable;
import java.util.List;

public class PageOrderDTO implements Serializable {

    private List<OrderResponseDTO> content;
    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private int numberOfElements;
    private boolean first;
    private boolean last;

    public PageOrderDTO() {
    }

    public PageOrderDTO(List<OrderResponseDTO> content, int number, int size, int totalPages,
                        long totalElements, int numberOfElements, boolean first, boolean last) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.numberOfElements = numberOfElements;
        this.first = first;
        this.last = last;
    }

    public List<OrderResponseDTO> getContent() {
        return content;
    }

    public void setContent(List<OrderResponseDTO> content) {
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