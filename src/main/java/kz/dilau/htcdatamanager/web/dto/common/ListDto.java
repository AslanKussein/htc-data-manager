package kz.dilau.htcdatamanager.web.dto.common;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListDto<T> {
    public static final ListDto NULL_OBJECT = null;

    @Setter(AccessLevel.NONE)
    private int size;
    @NonNull
    private List<T> data = new ArrayList<>();

    public ListDto(List<T> data) {
        if (nonNull(data) && !data.isEmpty()) {
            setData(data);
        }
    }

    public ListDto(Set<T> data) {
        if (nonNull(data) && !data.isEmpty()) {
            setData(new ArrayList<>(data));
        }
    }

    public void setData(List<T> data) {
        this.size = data.size();
        this.data = data;
    }

    public boolean isEmpty() {
        return size<=0;
    }
}
