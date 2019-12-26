package com.icspl.createsoc.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockVPModel {
        @SerializedName("BlockId")
        @Expose
        private String blockId;
        @SerializedName("SocietyId")
        @Expose
        private String societyId;
        @SerializedName("BlockName")
        @Expose
        private String blockName;

        public String getBlockId() {
            return blockId;
        }

        public void setBlockId(String blockId) {
            this.blockId = blockId;
        }

        public String getSocietyId() {
            return societyId;
        }

        public void setSocietyId(String societyId) {
            this.societyId = societyId;
        }

        public String getBlockName() {
            return blockName;
        }

        public void setBlockName(String blockName) {
            this.blockName = blockName;
        }

    }
