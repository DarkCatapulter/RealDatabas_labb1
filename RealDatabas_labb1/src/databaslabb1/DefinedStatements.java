/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaslabb1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swehu
 */
public class DefinedStatements {

    DBConnection dbcon = DatabasLabb1.dbcon;

    public DefinedStatements() {
        
    }

    public boolean login(String email, String password) {
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("SELECT COUNT(*) AS c FROM user WHERE Email = ? AND Password = Password(?);");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("c") == 1;
        } catch (SQLException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean register(String email, String password) {
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("INSERT INTO user(Email, Password) VALUES(?,Password(?));");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("c") == 1;
        } catch (SQLException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<Content> searchContent(String title, String genre, String creator, String rating) {
        ArrayList<Content> tmpArr = new ArrayList<>();

        try {
            PreparedStatement stmt;
            if (rating.equals("All")) {
                stmt = dbcon.con.prepareStatement("SELECT * FROM ("
                        + "SELECT content.contentid, content.type, content.title, content.addedby, content.releasedate, Group_concat(creator.creatorid) AS creatorID, Group_concat(creator.role) AS creatorRole, Group_concat(creator.nation) AS creatorNations, Group_concat(creator.addedby) AS creatorAddedBy, Group_concat(creator.NAME) AS creatorName, Group_concat(DISTINCT contentgenre.genre) AS genre FROM content, contentgenre, madeby, creator WHERE content.contentid = contentgenre.contentid AND ( madeby.contentid = content.contentid AND creator.creatorid = madeby.creatorid ) GROUP BY content.contentid) AS allContent"
                        + " WHERE Title LIKE ? AND genre LIKE ? AND creatorName LIKE ?;");
            } else {
                stmt = dbcon.con.prepareStatement("SELECT * FROM ("
                        + "SELECT content.contentid, content.type, content.title, content.addedby, content.releasedate, Group_concat(creator.creatorid) AS creatorID, Group_concat(creator.role) AS creatorRole, Group_concat(creator.nation) AS creatorNations, Group_concat(creator.addedby) AS creatorAddedBy, Group_concat(creator.NAME) AS creatorName, Group_concat(DISTINCT contentgenre.genre) AS genre, Avg(review.score) AS avgScore FROM content, contentgenre, madeby, creator, review WHERE content.contentid = contentgenre.contentid AND ( madeby.contentid = content.contentid AND creator.creatorid = madeby.creatorid ) AND ( content.contentid = review.contentid )  GROUP BY content.contentid) AS allContent"
                        + " WHERE Title LIKE ? AND genre LIKE ? AND creatorName LIKE ? AND avgScore LIKE ?;");
                stmt.setString(4, rating + "%");
            }
            stmt.setString(1, "%" + title + "%");
            stmt.setString(2, "%" + genre + "%");
            stmt.setString(3, "%" + creator + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PreparedStatement scorestmt = dbcon.con.prepareStatement("SELECT ContentID, AVG(review.Score) AS avgscore FROM review WHERE ContentID = ? GROUP BY ContentID");
                scorestmt.setString(1, rs.getString("ContentID"));
                ResultSet srs = scorestmt.executeQuery();

                String[] creatorIDs = rs.getString("CreatorID").split(",");
                String[] creatorNames = rs.getString("CreatorName").split(",");
                String[] creatorRoles = rs.getString("CreatorRole").split(",");
                String[] creatorNations = rs.getString("CreatorNations").split(",");
                String[] creatorAddedBy = rs.getString("CreatorAddedBy").split(",");
                String[] genres = rs.getString("genre").split(",");
                Content tmp;
                if (srs.next()) {
                    tmp = new Content(rs.getInt("ContentID"), ContentType.valueOf(rs.getString("Type").toUpperCase()), rs.getString("Title"), rs.getTimestamp("ReleaseDate"), rs.getString("AddedBy"), srs.getString("avgscore"));
                } else {
                    tmp = new Content(rs.getInt("ContentID"), ContentType.valueOf(rs.getString("Type").toUpperCase()), rs.getString("Title"), rs.getTimestamp("ReleaseDate"), rs.getString("AddedBy"), "0");
                }
                int i = 0;
                for (String creatorID : creatorIDs) {
                    if (!tmp.creatorExists(Integer.parseInt(creatorID))) {
                        tmp.addCreator(new Creator(Integer.parseInt(creatorID), creatorNames[i], creatorRoles[i], creatorNations[i], creatorAddedBy[i]));
                    }
                    i++;
                }
                for (String genre1 : genres) {
                    tmp.addGenre(genre1);
                }
                tmpArr.add(tmp);
            }

        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmpArr;
    }
    
    public void addReview(Content content, String text, String score){
        try {
            PreparedStatement stmt = dbcon.con.prepareStatement("INSERT INTO review(ContentID, UserEmail, Date, Text, Score) VALUES(?,?,?,?,?)");
            stmt.setInt(1, content.getId());
            stmt.setString(2, dbcon.getUser());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setString(4, text);
            stmt.setString(5, score);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addContent(Content content) {
        try {
            dbcon.con.setAutoCommit(false);
            PreparedStatement stmt = dbcon.con.prepareStatement("INSERT INTO content(Type, Title, ReleaseDate, AddedBy) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, content.getType().getValue());
            stmt.setString(2, content.getTitle());
            stmt.setTimestamp(3, content.getTimestamp());
            stmt.setString(4, content.getAddedBy());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    content.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("no ID obtained.");
                }
            }

            stmt = dbcon.con.prepareStatement("INSERT INTO creator(Role, Name, Nation, addedBy) VALUES(?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmt2 = dbcon.con.prepareStatement("INSERT INTO madeby(ContentID, creatorID) VALUES(?,?);");
            for (Creator creator : content.getCreators()) {
                stmt.setString(1, creator.getRole());
                stmt.setString(2, creator.getName());
                stmt.setString(3, creator.getNation());
                stmt.setString(4, creator.getAddedBy());
                stmt.executeUpdate();
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        creator.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("no ID obtained.");
                    }
                }
                stmt2.setInt(1, content.getId());
                stmt2.setInt(2, creator.getId());
                stmt2.executeUpdate();
            }
            stmt = dbcon.con.prepareStatement("INSERT INTO contentgenre(ContentID, Genre) VALUES(?,?)");
            for (String genre : content.getGenres()) {
                stmt.setInt(1, content.getId());
                stmt.setString(2, genre);
                stmt.executeUpdate();
            }
            dbcon.con.commit();

        } catch (SQLException ex) {
            try {
                dbcon.con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dbcon.con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(DefinedStatements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

