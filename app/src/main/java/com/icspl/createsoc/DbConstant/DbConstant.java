package com.icspl.createsoc.DbConstant;

import android.provider.BaseColumns;

public class DbConstant {
    public class Bill implements BaseColumns{
        //TABLE Name
        public  static final String TABLE_BILL="bill";
        //column
        public static final String ID="id";
        public static final String DESCRIPTION="description";
        public static final String LEDGER="ledger";
        public static final String GROUP="groups";
        public static final String EXPENSE_TYPE="expense_type";
        public static final String BASIS="basis";
        public static final String AMOUNT="amount";
    }
    public class SocietyDocuments implements BaseColumns{
        //table name
        public static final String TABLE_SOCIETY_DOCUMENT="society_document";
        //columns
        public static final String ID="id";
        public static final String TITLE="title";
        public static final String DESCRIPTION="description";
        public static final String DOCUMENT="document";
        public static final String PHOTO="photo";
        public static final String NUMBER="no";
        public static final String SOCIETY_ID="society_id";
    }
    public class FlatDetails implements BaseColumns{
        //table name
        public static final String TABLE_FLAT_DETAILS="flat_details";
        //columns
        public static final String ID="id";
        public static final String BLOCK_ID="block_id";
        public static final String WING_ID="wing_id";
        public static final String FLAT_NAME="flat_name";
        public static final String FLAT_NUMBER="flat_number";
        public static final String NO_OF_OCCUPANTS="no_of_occupants";
        public static final String BUILD_UP_AREA="build_up_area";
        public static final String CARPET_AREA="carpet_area";
    }
    public class OwnerDetails implements BaseColumns{
        //table name
        public static final String TABLE_OWNER_DETAILS="table_ownerdetails";
        //columns
        public static final String ID="id";
        public static final String FID="fid";
        public static final String ONAME="oname";
        public static final String ODOB="odob";
        public static final String OEMAIL="oemail";
        public static final String OMOBNO="omobno";
        public static final String OLANDLINE="olandline";
        public static final String OADD="oadd";
        public static final String OAADHAR="oaadhar";
        public static final String OPAN="opan";
        public static final String ORELATION="orelation";
        public static final String OTYPE="otype";
        public static final String VOTING_RIGHT="votingright";
        public static final String ATTENDING_MEETING="attendmeeting";
        public static final String RELATIVE_NAME="relativename";
        public static final String RELATIVE_NO="relativeno";
        public static final String RELATIVE_LANDLINE="relativelandline";
        public static final String RELATIVE_EMAIL="relativemail";
    }
    public class OwnerDocuments implements BaseColumns{
        //table name
        public static final String TABLE_SOCIETY_DOCUMENT="owner_document";
        //columns
        public static final String ID="id";
        public static final String TITLE="title";
        public static final String DESCRIPTION="description";
        public static final String DOCUMENT="document";
        public static final String PHOTO="photo";
        public static final String NUMBER="no";
        public static final String FLAT_ID="flat_id";
    }
}
