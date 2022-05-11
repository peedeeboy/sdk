/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.materialdefinition.fileStructure;

import com.jme3.gde.materialdefinition.fileStructure.leaves.DefineBlock;
import com.jme3.util.blockparser.Statement;
import java.util.List;

/**
 *
 * @author rickard
 */
public class DefinesBlock extends UberStatement {
    
    protected DefinesBlock(int lineNumber, String line) {
        super(lineNumber, line);
    }
    
    public DefinesBlock(Statement sta) {
        this(sta.getLineNumber(), sta.getLine());
        for (Statement statement : sta.getContents()) {
            addStatement(new DefineBlock(statement));
        }
    }
    
    public List<DefineBlock> getDefineBlocks() {
        return getBlocks(DefineBlock.class);
    }
    
    public void addDefineBlock(DefineBlock block) {
        contents.add(block);
    }
    
    public void removeDefineBlock(DefineBlock block) {
        contents.remove(block);
    }
}
