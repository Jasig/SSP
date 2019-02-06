package org.jasig.ssp.service.uportal;

public class Permission {

    private final String owner;

    private final String activity;

    private final String principal;

    private final String target;

    private final boolean inherited;

    public Permission(String owner, String activity, String principal, String target, boolean inherited) {
        this.owner = owner;
        this.activity = activity;
        this.principal = principal;
        this.target = target;
        this.inherited = inherited;
    }

    public String getOwner() {
        return owner;
    }

    public String getActivity() {
        return activity;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getTarget() {
        return target;
    }

    public boolean isInherited() {
        return inherited;
    }

}
