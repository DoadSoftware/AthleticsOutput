package com.athletics.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Match")
@XmlAccessorType(XmlAccessType.FIELD)
public class Match {

  @XmlTransient
  private List<NameSuper> nameSuper;

public List<NameSuper> getNameSuper() {
	return nameSuper;
}

public void setNameSuper(List<NameSuper> nameSuper) {
	this.nameSuper = nameSuper;
}
  
}