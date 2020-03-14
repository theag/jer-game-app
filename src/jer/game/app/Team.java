/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jer.game.app;

import java.util.Objects;

/**
 *
 * @author nbp184
 */
public class Team implements Comparable<Team> {
    
    static final int STARTING_POINTS = 5;
    
    final String name;
    final int members;
    int points;
    int artifactCount;
    
    Team(int members, int number) {
        this.members = members;
        points = STARTING_POINTS;
        artifactCount = 1;
        name = "" +number;
    }
    
    Team(String name) {
        this.name = name;
        members = 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Team) {
            Team t = (Team)o;
            return t.name.compareToIgnoreCase(name) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.name.toLowerCase());
        return hash;
    }

    @Override
    public int compareTo(Team o) {
        return name.compareToIgnoreCase(o.name);
    }
    
}
