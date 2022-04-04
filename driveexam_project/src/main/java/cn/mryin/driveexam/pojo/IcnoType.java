package cn.mryin.driveexam.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IcnoType implements Serializable {
    private Integer icnoTypeId;

    private String icnoTypeName;

    private Integer icnoParent;

    private int isList;

    public int getIsList() {
        return isList;
    }

    public void setIsList(int isList) {
        this.isList = isList;
    }

    private IcnoType parentType;

    private List<Icno> icnos;

    private String level;

    private List<IcnoType> children;

    public List<IcnoType> getChildren() {
        return children;
    }

    public void setChildren(List<IcnoType> children) {
        this.children = children;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Icno> getIcnos() {
        return icnos;
    }

    public void setIcnos(List<Icno> icnos) {
        this.icnos = icnos;
    }

    public Integer getIcnoTypeId() {
        return icnoTypeId;
    }

    public void setIcnoTypeId(Integer icnoTypeId) {
        this.icnoTypeId = icnoTypeId;
    }

    public String getIcnoTypeName() {
        return icnoTypeName;
    }

    public void setIcnoTypeName(String icnoTypeName) {
        this.icnoTypeName = icnoTypeName;
    }

    public Integer getIcnoParent() {
        return icnoParent;
    }

    public void setIcnoParent(Integer icnoParent) {
        this.icnoParent = icnoParent;
    }

    public IcnoType getParentType() {
        return parentType;
    }

    public void setParentType(IcnoType parentType) {
        this.parentType = parentType;
    }
}