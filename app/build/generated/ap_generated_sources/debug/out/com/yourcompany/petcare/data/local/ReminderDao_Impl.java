package com.yourcompany.petcare.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.yourcompany.petcare.data.model.Reminder;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Reminder> __insertionAdapterOfReminder;

  private final EntityDeletionOrUpdateAdapter<Reminder> __deletionAdapterOfReminder;

  private final EntityDeletionOrUpdateAdapter<Reminder> __updateAdapterOfReminder;

  private final SharedSQLiteStatement __preparedStmtOfSetDone;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTrigger;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReminder = new EntityInsertionAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `reminders` (`id`,`title`,`notes`,`ownerEmail`,`dateTimeMillis`,`soundUri`,`recurringDaily`,`done`,`petId`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reminder entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNotes());
        }
        if (entity.getOwnerEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getOwnerEmail());
        }
        statement.bindLong(5, entity.getDateTimeMillis());
        if (entity.getSoundUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSoundUri());
        }
        final int _tmp = entity.isRecurringDaily() ? 1 : 0;
        statement.bindLong(7, _tmp);
        final int _tmp_1 = entity.isDone() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getPetId());
      }
    };
    this.__deletionAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reminders` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reminder entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfReminder = new EntityDeletionOrUpdateAdapter<Reminder>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reminders` SET `id` = ?,`title` = ?,`notes` = ?,`ownerEmail` = ?,`dateTimeMillis` = ?,`soundUri` = ?,`recurringDaily` = ?,`done` = ?,`petId` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Reminder entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getNotes());
        }
        if (entity.getOwnerEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getOwnerEmail());
        }
        statement.bindLong(5, entity.getDateTimeMillis());
        if (entity.getSoundUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSoundUri());
        }
        final int _tmp = entity.isRecurringDaily() ? 1 : 0;
        statement.bindLong(7, _tmp);
        final int _tmp_1 = entity.isDone() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getPetId());
        statement.bindLong(10, entity.getId());
      }
    };
    this.__preparedStmtOfSetDone = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE reminders SET done = 1 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTrigger = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE reminders SET dateTimeMillis = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Reminder r) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfReminder.insertAndReturnId(r);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Reminder r) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfReminder.handle(r);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Reminder r) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfReminder.handle(r);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void setDone(final long id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetDone.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfSetDone.release(_stmt);
    }
  }

  @Override
  public void updateTrigger(final long id, final long nextEpoch) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTrigger.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, nextEpoch);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, id);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateTrigger.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Reminder>> getRemindersForUser(final String ownerEmail) {
    final String _sql = "SELECT * FROM reminders WHERE ownerEmail = ? ORDER BY dateTimeMillis ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ownerEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ownerEmail);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"reminders"}, false, new Callable<List<Reminder>>() {
      @Override
      @Nullable
      public List<Reminder> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfOwnerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerEmail");
          final int _cursorIndexOfDateTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTimeMillis");
          final int _cursorIndexOfSoundUri = CursorUtil.getColumnIndexOrThrow(_cursor, "soundUri");
          final int _cursorIndexOfRecurringDaily = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringDaily");
          final int _cursorIndexOfDone = CursorUtil.getColumnIndexOrThrow(_cursor, "done");
          final int _cursorIndexOfPetId = CursorUtil.getColumnIndexOrThrow(_cursor, "petId");
          final List<Reminder> _result = new ArrayList<Reminder>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Reminder _item;
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpOwnerEmail;
            if (_cursor.isNull(_cursorIndexOfOwnerEmail)) {
              _tmpOwnerEmail = null;
            } else {
              _tmpOwnerEmail = _cursor.getString(_cursorIndexOfOwnerEmail);
            }
            final long _tmpDateTimeMillis;
            _tmpDateTimeMillis = _cursor.getLong(_cursorIndexOfDateTimeMillis);
            final String _tmpSoundUri;
            if (_cursor.isNull(_cursorIndexOfSoundUri)) {
              _tmpSoundUri = null;
            } else {
              _tmpSoundUri = _cursor.getString(_cursorIndexOfSoundUri);
            }
            final boolean _tmpRecurringDaily;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfRecurringDaily);
            _tmpRecurringDaily = _tmp != 0;
            _item = new Reminder(_tmpTitle,_tmpNotes,_tmpOwnerEmail,_tmpDateTimeMillis,_tmpSoundUri,_tmpRecurringDaily);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final boolean _tmpDone;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfDone);
            _tmpDone = _tmp_1 != 0;
            _item.setDone(_tmpDone);
            final long _tmpPetId;
            _tmpPetId = _cursor.getLong(_cursorIndexOfPetId);
            _item.setPetId(_tmpPetId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public int countRemindersDone(final String ownerEmail, final long now) {
    final String _sql = "SELECT COUNT(*) FROM reminders WHERE ownerEmail = ? AND dateTimeMillis <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (ownerEmail == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ownerEmail);
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, now);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Reminder getReminder(final long id) {
    final String _sql = "SELECT * FROM reminders WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final int _cursorIndexOfOwnerEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "ownerEmail");
      final int _cursorIndexOfDateTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dateTimeMillis");
      final int _cursorIndexOfSoundUri = CursorUtil.getColumnIndexOrThrow(_cursor, "soundUri");
      final int _cursorIndexOfRecurringDaily = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringDaily");
      final int _cursorIndexOfDone = CursorUtil.getColumnIndexOrThrow(_cursor, "done");
      final int _cursorIndexOfPetId = CursorUtil.getColumnIndexOrThrow(_cursor, "petId");
      final Reminder _result;
      if (_cursor.moveToFirst()) {
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        final String _tmpOwnerEmail;
        if (_cursor.isNull(_cursorIndexOfOwnerEmail)) {
          _tmpOwnerEmail = null;
        } else {
          _tmpOwnerEmail = _cursor.getString(_cursorIndexOfOwnerEmail);
        }
        final long _tmpDateTimeMillis;
        _tmpDateTimeMillis = _cursor.getLong(_cursorIndexOfDateTimeMillis);
        final String _tmpSoundUri;
        if (_cursor.isNull(_cursorIndexOfSoundUri)) {
          _tmpSoundUri = null;
        } else {
          _tmpSoundUri = _cursor.getString(_cursorIndexOfSoundUri);
        }
        final boolean _tmpRecurringDaily;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfRecurringDaily);
        _tmpRecurringDaily = _tmp != 0;
        _result = new Reminder(_tmpTitle,_tmpNotes,_tmpOwnerEmail,_tmpDateTimeMillis,_tmpSoundUri,_tmpRecurringDaily);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _result.setId(_tmpId);
        final boolean _tmpDone;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfDone);
        _tmpDone = _tmp_1 != 0;
        _result.setDone(_tmpDone);
        final long _tmpPetId;
        _tmpPetId = _cursor.getLong(_cursorIndexOfPetId);
        _result.setPetId(_tmpPetId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
