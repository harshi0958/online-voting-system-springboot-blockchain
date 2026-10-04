	package com.example.voting.blockchain;
	
	
	import java.util.ArrayList;
	import java.util.List;
	import org.springframework.stereotype.Component;
	import org.springframework.stereotype.Component;
	import org.springframework.stereotype.Component;

	@Component   // 🔥 ADD THIS
	public class Blockchain {
	    private List<Block> chain;
	
	    public Blockchain() {
	        this.chain = new ArrayList<>();
	        this.chain.add(generateGenesisBlock());
	    }
	
	    private Block generateGenesisBlock() {
	        return new Block("Genesis Block", "0");
	    }
	
	    public void addBlock(String data) {
	        Block lastBlock = this.chain.get(this.chain.size() - 1);
	        Block newBlock = new Block(data, lastBlock.getHash());
	        this.chain.add(newBlock);
	    }
	
	    public boolean isChainValid() {
	        for (int i = 1; i < this.chain.size(); i++) {
	            Block current = this.chain.get(i);
	            Block previous = this.chain.get(i - 1);
	            if (!current.getHash().equals(current.calculateHash()) ||
	                    !current.getPreviousHash().equals(previous.getHash())) {
	                return false;
	            }
	        }
	        return true;
	    }
	
	    public List<Block> getChain() {
	        return chain;
	    }
	}