/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jer.game.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author nbp184
 */
public class BaseFight implements Iterable<Team> {
    
    private static final Random rand = new Random();
    
    final Team defender;
    int defenders;
    private final Team[] attackers;
    private final int[] attackSize;
    private int attackerCount;
    private int attackerTotal;
    
    BaseFight(int size, Team defender) {
        this.defender = defender;
        attackers = new Team[size];
        attackSize = new int[size];
        attackerCount = 0;
        attackerTotal = 0;
    }
    
    void addAttacker(Team t, int size) {
        attackers[attackerCount] = t;
        attackSize[attackerCount++] = size;
        attackerTotal += size;
    }
    
    Team resolveFight() {
        if(attackerTotal > 0 && (attackerTotal > defenders || (attackerTotal == defenders && rand.nextBoolean()))) {
            Team[] winning = new Team[attackerCount];
            int winningSize = 0;
            int winningAttack = 0;
            for(int i = 0; i < attackerCount; i++) {
                if(attackSize[i] > winningAttack) {
                    winningAttack = attackSize[i];
                    winning[0] = attackers[i];
                    winningSize = 1;
                } else if(attackSize[i] == winningAttack) {
                    winning[winningSize++] = attackers[i];
                }
            }
            if(winningSize == 1) {
                return winning[0];
            } else {
                return winning[rand.nextInt(winningSize)];
            }
        } else {
            return defender;
        }
    }

    @Override
    public Iterator<Team> iterator() {
        ArrayList<Team> a = new ArrayList<>();
        for(int i = 0; i < attackerCount; i++) {
            a.add(attackers[i]);
        }
        return a.iterator();
    }

    int getAttackerTotal() {
        return attackerTotal;
    }
    
}
