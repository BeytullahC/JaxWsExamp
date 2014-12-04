/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.bean;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 912867
 */
@XmlRootElement

public class TestBean {

    public TestBean() {
    }

    public TestBean(String name, Integer version) {
        this.name = name;
        this.version = version;
    }

    @XmlElement(name = "name", required = true, nillable = true)
    private String name;
    @XmlElement(name = "version", required = true, nillable = true)
    private Integer version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestBean [name=");
		builder.append(name);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}

}
