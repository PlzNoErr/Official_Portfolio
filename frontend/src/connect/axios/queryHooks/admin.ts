import { $ } from "@/connect/axios/setting";
import { useInfiniteQuery, useMutation, useQuery } from "@tanstack/react-query";
import { PagableResponse } from "@/types/mypage";
import { useEffect, useMemo, useState } from "react";

interface UserSeq {
  userSeq: number;
}

interface selectedUser {
  userNickname: string;
  userSeq: number;
}

interface User extends UserSeq {
  userCreatedAt: string;
  userEmail: string;
  userIsBan: boolean;
  userIsChattingBan: boolean;
  userIsUser: boolean;
  useNickname: string;
}

// 회원 전체 관리(관리자)
export function getUsers() {

  const [searchInput, setSearchInput] = useState<string>("");
  const [searchSelectValue, setSearchSelectValue] = useState<string>("all");
  const [selectedUserList, setSelectedUserList] = useState<selectedUser[]>([]);
  const [changeSelectValue, setChangeSelectValue] = useState<string>("ban");

  const { data, isLoading, refetch, fetchNextPage, isFetchingNextPage } =
  useInfiniteQuery(
    ["getAllUsers"],
    async ({ pageParam = 0 }): Promise<PagableResponse<User>> => {
      const { data } = await $.get(
        `/admin/user?page=${pageParam}&userNickname=${searchInput}&isChattingBan=${isChattingBan}&isBan=${isBan}`
      );
      return data;
    },
    {
      getNextPageParam: ({ number, last }) => {
        if (!last) return number + 1;
      },
    }
  );

  // 유저 리스트
  const userList = useMemo(() => {
    const result: User[] = [];
    data?.pages.forEach((page) => {
      page.content.forEach((item) => {
        result.push(item);
      });
    });

    return result;
  }, [data]);

  // 선택된 유저는 제외한 리스트
  const filtered_userList = useMemo(() => {
    const set = new Set(selectedUserList.map((v) => v.userSeq));
    return userList?.filter((v) => !set.has(v.userSeq));
  }, [userList, selectedUserList]);

  // select box value
  let isChattingBan: boolean | null = null;
  let isBan: boolean | null = null;

  useEffect(() => {
    if (searchSelectValue === "banChat") {
      isChattingBan = true;
    } else if (searchSelectValue === "notBanChat") {
      isChattingBan = false;
    }

    if (searchSelectValue === "ban") {
      isBan = true;
    } else if (searchSelectValue === "notBan") {
      isBan = false;
    }
  }, [searchSelectValue]);



  // mutation
  const banMutation = (url: string) => {
    const { mutate } = useMutation(
      async (): Promise<void> => {
        const { data } = await $.put(
          url,
          filtered_userList?.map((v) => {
            v.userSeq;
          })
        );
        return data;
      },
      {
        onSuccess: () => {
          refetch();
        },
      }
    );

    return mutate;
  };

  const chattingBanMutate = banMutation(`/admin/user/chatting-ban`);
  const chattingNotBanMutate = banMutation(`/admin/user/not-chatting-ban`);
  const BanMutate = banMutation(`/admin/user/ban`);
  const NotBanMutate = banMutation(`/admin/user/not-ban`);



  return {
    isLoading,
    userList,
    searchInput,
    searchSelectValue,
    isFetchingNextPage,
    selectedUserList,
    changeSelectValue,
    filtered_userList,
    refetch,
    setSearchInput,
    setSearchSelectValue,
    chattingBanMutate,
    chattingNotBanMutate,
    BanMutate,
    NotBanMutate,
    fetchNextPage,
    setSelectedUserList,
    setChangeSelectValue,
  };
}
