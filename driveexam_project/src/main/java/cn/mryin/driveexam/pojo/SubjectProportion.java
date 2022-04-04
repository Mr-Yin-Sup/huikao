package cn.mryin.driveexam.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectProportion extends SubjectProportionKey {
    private Byte proportion;

    private Chapter chapter;

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Byte getProportion() {
        return proportion;
    }

    public void setProportion(Byte proportion) {
        this.proportion = proportion;
    }
}